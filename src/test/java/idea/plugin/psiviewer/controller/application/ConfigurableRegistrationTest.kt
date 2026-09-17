package idea.plugin.psiviewer.controller.application

import com.intellij.ide.plugins.PluginManagerCore
import com.intellij.openapi.extensions.PluginDescriptor
import com.intellij.openapi.extensions.PluginId
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.options.ConfigurableEP
import com.intellij.testFramework.fixtures.BasePlatformTestCase

/**
 * Checks that the configurables registered in `META-INF/plugin.xml` can be shown without loading their classes.
 */
class ConfigurableRegistrationTest : BasePlatformTestCase() {
  fun testConfigurablesDeclareDisplayNameInPluginXml() {
    val plugin = PluginManagerCore.getPlugin(PluginId.getId(PLUGIN_ID))
    assertNotNull("Plugin $PLUGIN_ID is not loaded", plugin)
    val configurables = pluginConfigurables(plugin!!)
    assertFalse("No configurables contributed by ${plugin.name}", configurables.isEmpty())
    for (ep in configurables) {
      assertTrue(
        "${ep.instanceClass} must declare 'displayName' or 'key'/'bundle' in plugin.xml",
        ep.displayName != null || (ep.key != null && ep.bundle != null)
      )
    }
  }

  private fun pluginConfigurables(plugin: PluginDescriptor): List<ConfigurableEP<Configurable>> =
    Configurable.APPLICATION_CONFIGURABLE.extensionList.filter { it.pluginDescriptor.pluginId == plugin.pluginId }

  companion object {
    /** Plugin id implied by `<name>` in `META-INF/plugin.xml`, which declares no explicit `<id>`. */
    private const val PLUGIN_ID = "PsiViewer"
  }
}
