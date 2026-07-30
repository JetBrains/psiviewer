# PsiViewer

IntelliJ Platform plugin: a tool window that shows the PSI (Program Structure Interface) tree of the current file/element, plus a property sheet for the selected node. Aimed at plugin developers. JetBrains-maintained fork of the original sylvanaar plugin.

## Build & run

- Gradle + `org.jetbrains.intellij.platform` plugin (see `build.gradle.kts`). Java/Kotlin toolchain from `gradle.properties` (`javaToolchain`).
- Kotlin is enabled (`org.jetbrains.kotlin.jvm`). Mixed Java/Kotlin sources both live under `src/main/java`. Kotlin stdlib is NOT bundled (`kotlin.stdlib.default.dependency=false`) — it comes from the target platform; that property is read by the Kotlin plugin and checked by `verifyPluginProjectConfiguration`.
- Target platform is set in `gradle.properties` (`platformVersion`/`platformBranch`, currently IU 2026.2). Bumps to platform/gradle are their own commits (see git log).
- Sandbox lives under `.intellijPlatform/sandbox/`.
- Common tasks: `./gradlew build`, `./gradlew test`, `./gradlew runIde`, `./gradlew verifyPlugin`.
- `plugin-changes.html` feeds `changeNotes`; `psiViewerDescription` in `gradle.properties` feeds the plugin description.

## Layout (`src/main/java`)

MVC-ish split under `idea.plugin.psiviewer`:

- `controller/actions/` — IDE actions. `ViewElementAtCaretAction` (Ctrl+Shift+C), `ViewFileElementAction` (Ctrl+Shift+Q), `ViewCurrentElementAction` (Ctrl+Shift+P), `PsiDump`/`PsiTestDump` (copy tree to clipboard). `BaseGlobalAction` is the shared base; `PropertyToggleAction` backs the toolbar toggles.
- `controller/application/` — `PsiViewerApplicationSettings` (app-level persistent settings) + `Configuration` (its `applicationConfigurable`).
- `controller/project/` — project-scoped wiring:
  - `PsiViewerProjectService` — the central project service. Holds per-project `State` (`@State`/workspace-file persisted: highlight, filter whitespace, show properties, split divider, autoscroll to/from source), builds the toolbar + language combo box, owns the `PsiViewerPanel`, subscribes to editor/caret/PSI-tree listeners. `getInstance(project)` accessor.
  - `PsiViewerToolWindowFactory` — registers the "PsiViewer" tool window (right anchor); hands the panel to the tool window content.
  - `PsiViewerEditorListener` — reacts to editor/caret changes. `PsiViewerTreeChangeListener` — reacts to PSI changes.
- `model/PsiViewerTreeModel` — Swing `TreeModel` over PSI. Children computed lazily via `ReadAction`; whitespace filtering lives in `isValid`.
- `view/` — Swing UI. `PsiViewerPanel` (toolbar + tree + property sheet in a `JSplitPane`; core selection/highlight/caret-sync logic), `PsiViewerTree` + `PsiViewerTreeCellRenderer`, `PropertySheetPanel`/`PropertySheetHeaderRenderer`/`PropertySheetToolTip*` (reflection-based property display of the selected PSI element), `EditorPsiElementHighlighter` + `EditorCaretMover` (highlight element / move caret in the source editor), `IconCache`, `configuration/` (settings UI: alpha chooser, sliders).
- `util/` — `Helpers` (icons etc.), `IntrospectionUtil` + `PluginPsiUtil` (reflection over PSI for the property sheet), `ActionEventUtil`.
- `PsiViewerConstants` — shared ids/keys/icon paths (`ID_TOOL_WINDOW`, action group/toolbar ids, title prefixes). Referenced widely via static import.
- `com.sylvanaar.idea.errorreporting` — legacy error-report submitter (`YouTrackBugReporter` registered as `errorHandler`).

`src/main/resources/META-INF/plugin.xml` — registrations (extensions, actions, keymap shortcuts). `src/main/resources/images/` — icons.

## Key flows

- **Selection sync**: `PsiViewerPanel` keeps tree selection, source-editor caret, highlighter, and property sheet in sync. The `inSetSelectedElement` guard + the `CARET_MOVED`/`TREE_SELECTION_CHANGED` reason strings prevent feedback loops between caret moves and tree selection. Touch carefully.
- **Root element**: for a `PsiFile`, the language combo box picks which view-provider language root is shown (`setRootElement` / `selectElementAtCaret`).
- All PSI access must be under a read action (see `PsiViewerTreeModel.getFilteredChildren`).

## Tests

`src/test/java` — platform test framework (JUnit4). `PropertySheetPanelTest` is the existing example. Prefer functional/behavioral tests over unit tests.

## Performance notes

- A PSI file can contain thousands of elements (e.g. a huge malformed comment). Anything driven by PSI-tree-change or caret events must assume large, deep trees.
- `PsiViewerTreeChangeListener` reacts to *every* PSI change, several fire per keystroke — refreshes must be debounced (`Flow.debounce` on the service's `CoroutineScope`) and run on `Dispatchers.EDT`, never synchronously and never inside a `runWriteAction` (a read-only viewer takes no write lock).
- `PsiViewerTreeModel` backs a Swing tree over PSI; be mindful that `getChild`/`getChildCount`/`getIndexOfChild` are called repeatedly by the tree UI, so their per-call cost multiplies.

## Conventions

- Services can receive a platform-managed `CoroutineScope` via constructor injection (`PsiViewerProjectService(Project, CoroutineScope)`), cancelled on service disposal. Prefer it over hand-rolled scopes / `Alarm` / `MergingUpdateQueue`.

## Reference

The IntelliJ monorepo source is checked out locally as a sibling of this repo (~same platform revision, IU 2026.2) — use it to look up platform APIs and how the platform itself uses them.
