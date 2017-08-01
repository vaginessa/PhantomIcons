package highridas.phantom.icons.items;

/**
 * Created by Patrick Ridge on 2017-07-24.
 */
public class LauncherListItem {
    public final String name;
    public final String packageName;
    private boolean isInstalled = false;

    public LauncherListItem(String[] values, boolean isInstalled) {
        name = values[0];
        packageName = values[1];
        this.isInstalled = isInstalled;
    }

    public String getName() {
        return name;
    }

    public String getPackageName() {
        return packageName;
    }

    public boolean isInstalled() {
        return isInstalled;
    }
}
