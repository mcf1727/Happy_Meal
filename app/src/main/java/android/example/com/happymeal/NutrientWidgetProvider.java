package android.example.com.happymeal;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class
NutrientWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

//        CharSequence widgetText = context.getString(R.string.appwidget_text);
//        // Construct the RemoteViews object
//        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.nutrient_widget_provider);
//        views.setTextViewText(R.id.tv_widget_nutrient, widgetText);
//
//        // Instruct the widget manager to update the widget
//        appWidgetManager.updateAppWidget(appWidgetId, views);


        RemoteViews remoteViews;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String mRecentNutrition = sharedPreferences.getString("recent_nutrition", "NoRecentNutrition");
        if (mRecentNutrition.equals("NoRecentNutrition")) {
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_nutrition_nutrients);
            remoteViews.setOnClickPendingIntent(R.id.rl_recipe_ingredients_widget, pendingIntent);
        } else {
            remoteViews = getNutritionListRemoteView(context);
        }

        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    private static RemoteViews getNutritionListRemoteView(Context context) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_nutrition_nutrients);
        Intent intent = new Intent(context, ListWidgetService.class);
        remoteViews.setRemoteAdapter(R.id.lv_widget_nutrients_list, intent);

        Intent appIntent = new Intent(context, DetailActivity.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context,0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setPendingIntentTemplate(R.id.lv_widget_nutrients_list, appPendingIntent);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String mRecentNutrition = sharedPreferences.getString("recent_nutrition", "NoRecentNutrition");
        remoteViews.setTextViewText(R.id.tv_widget_name, mRecentNutrition);
        remoteViews.setOnClickPendingIntent(R.id.tv_widget_name, appPendingIntent);

        remoteViews.setEmptyView(R.id.lv_widget_nutrients_list, R.id.empty_view_widget);
        return remoteViews;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        updateNutritionWidgets(context, appWidgetManager, appWidgetIds);
    }

    public static void updateNutritionWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}