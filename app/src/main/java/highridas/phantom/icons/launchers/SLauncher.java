package highridas.phantom.icons.launchers;

import android.content.Context;
import android.content.Intent;

import highridas.phantom.icons.R;


public class SLauncher {
    public SLauncher(Context context) {
        Intent s = new Intent("com.s.launcher.APPLY_ICON_THEME");
        s.putExtra("com.s.launcher.theme.EXTRA_PKG", context.getPackageName());
        s.putExtra("com.s.launcher.theme.EXTRA_NAME", context.getResources().getString(R.string.theme_title));
        context.startActivity(s);

    }
}
