package de.dhbw.container;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import de.dhbw.achievement.AchievementFragment;
import de.dhbw.auswertung.TotalEvaluationFragment;
import de.dhbw.contents.LiveTrackingFragment;

public class MenuContainerActivity extends SherlockFragmentActivity {

	//Menü
	DrawerLayout mDrawerLayout;
	//Menüpunkte
	ListView mDrawerList;

	ActionBarDrawerToggle mDrawerToggle;
	SherlockFragment tracking_from_menu;

	//Variable zur Sperre vom Menü und Achievements während eines Live-Trackings
	private boolean isLocked = false;
	
	//Menüüberschriften
	private String[] mNavigationTitles;

	//Setter für Menüsperrvariable
	public boolean isLocked() {
		return isLocked;
	}
	
	//Getter für Menüsperrvariable
	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Container Activity laden
		setContentView(R.layout.menu_container);
		
		//Menüüberschrift laden
		mNavigationTitles = getResources().getStringArray(
				R.array.navigation_array);
		//Menü laden
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		
		//Menüpunkte laden
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		
		//Appnamen auf Actionbar laden
		getSupportActionBar().setTitle(R.string.app_name);

		// Überlappen der Seiteninhalte beim Aufklappen des Menüs
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		// Menüpunkte initialisieren und (Click-)Listener aufsetzen
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.menu_list_item, mNavigationTitles));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, /* Container Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image */
		R.string.drawer_open, /* "open drawer"  */
		R.string.drawer_close /* "close drawer" */
		) {
			public void onDrawerClosed(View view) {
				invalidateOptionsMenu(); // ruft onPrepareOptionsMenu() auf
			}

			public void onDrawerOpened(View drawerView) {
				invalidateOptionsMenu(); // ruft onPrepareOptionsMenu() auf
			}
		};
		
		//Menülistener aktiviren
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			selectItem(0);
		}

	}

	//Beim Laden des Menüs im Container Activity icons einblenden
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		//Music icon und Achievements icon zum Menü hinzufügen
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.container, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	// Aufruf beim Sperren und Entsperren des Menüs, Musik icons und Achievement icons
	@Override
	public boolean onPrepareOptionsMenu(final Menu menu) {
		
	    MenuItem menuItem = menu.findItem(R.id.action_achievements);

    	menuItem.setEnabled(true);
    	mDrawerList.setEnabled(true);

	    return super.onPrepareOptionsMenu(menu);
	}

	//Definiert Aktionen beim Auswählen von icons auf Action Bar 
	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {

		switch (item.getItemId()) 
		{
			//Men�liste
			case android.R.id.home:
				//falls Men� schon ge�ffnet zumachen
				if (mDrawerLayout.isDrawerOpen(mDrawerList))
					mDrawerLayout.closeDrawer(mDrawerList);
				else
					if (!isLocked)	//Men� nur �ffnen falls nicht gesperrt (w�hrend eines Live-Tracking)
						mDrawerLayout.openDrawer(mDrawerList);
				break;
				
			//Musik icon
			case R.id.action_music_player: 
	
				//Sperre Musik-Icon w�hrend des Trackings
				if (!isLocked) 
				{
					//
					Intent intent = new Intent();
					intent.setAction("android.intent.action.MAIN");
					intent.addCategory("android.intent.category.APP_MUSIC");
					startActivity(intent);
				}
				break;
	
			//Achievement icon
			case R.id.action_achievements:
	
				if (!isLocked) 
				{
					// Backstack leeren
					if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
						getSupportFragmentManager().popBackStack();
					}
					
					//Ersetze aktuelles Fragment mit Achievement Fragment
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.currentFragment, new AchievementFragment())
							.commit();
				}
				break;
	
			default:
				;
		}

		return super.onOptionsItemSelected(item);
	}

	// Click Listener für ListView im Navigation Drawer
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}
	
	//	Zurück Button blockieren
	@Override
    public void onBackPressed() {
		((LiveTrackingFragment) tracking_from_menu).onBackPressed();
    	//super.onBackPressed();
    }

	//Auto-Created method
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	//Auto-Created method
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	//Definiert Aktionen beim Auswählen von Menüpunkten (Live Tracking, Auswertung)
	private void selectItem(int position) {

		switch (position) {

		// LiveTracking-Fragment
		case 0:
			tracking_from_menu = new LiveTrackingFragment();

			//Backstack leeren
			if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
				getSupportFragmentManager().popBackStack();
			}
			//Dynamisches Live-Tracking laden, entspricht auch der (App-) Hauptseite
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.currentFragment, tracking_from_menu).commit();
			break;
			
		// Auswertungs-Fragment
		case 1:
			//Backstack leeren
			if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
				getSupportFragmentManager().popBackStack();
			}
			//Zur detaillierten Auswertung
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.currentFragment,
							new TotalEvaluationFragment()).commit();
			break;
		default:
			
			//Default (App-) Hauptseite (-fragment), dynamisches Live-Tracking Fragment 
			SherlockFragment tracking_at_launch = new LiveTrackingFragment();
			getSupportFragmentManager().beginTransaction()
					.add(R.id.currentFragment, tracking_at_launch).commit();
		}
		
		//Nach dem Öffnen des jeweiligen Menüpunktes Menü schließen
		mDrawerLayout.closeDrawer(mDrawerList);
	}

}