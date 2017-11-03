package app.android.mmauri.laboratorio2.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.android.mmauri.laboratorio2.R;
import app.android.mmauri.laboratorio2.adapters.FruitAdapter;
import app.android.mmauri.laboratorio2.models.Fruit;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    // ListView, GridView y Adapters
    private ListView listView;
    private GridView gridView;
    private FruitAdapter adapterListView;
    private FruitAdapter adapterGridView;

    // Lista de nuestro modelo, fruta
    private List<Fruit> fruits;

    // Items en el option menu, para tenerlos a mano cuando tengamos que ocultar/mostrar-los
    private MenuItem itemListView;
    private MenuItem itemGridView;

    // Variables
    private int counter = 0;
    private final int SWITCH_TO_LIST_VIEW = 0;
    private final int SWITCH_TO_GRID_VIEW = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Mostrar icono
        menuInitialization();

        // Inicializar variables
        this.fruits = getAllFruits();

        this.listView = (ListView) findViewById(R.id.listViewFruits);
        this.gridView = (GridView) findViewById(R.id.gridViewFruits);

        // Adjuntando el mismo metodo click para ambos
        this.listView.setOnItemClickListener(this);
        this.gridView.setOnItemClickListener(this);

        this.adapterListView = new FruitAdapter(this, R.layout.list_item, fruits);
        this.adapterGridView = new FruitAdapter(this, R.layout.grid_item, fruits);

        this.listView.setAdapter(adapterListView);
        this.gridView.setAdapter(adapterGridView);

        // Registramos el context menu para las vistas
        registerForContextMenu(this.listView);
        registerForContextMenu(this.gridView);
    }

    private void menuInitialization() {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_myicon);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.clickFruit(this.fruits.get(position));
    }

    private void clickFruit(Fruit fruit) {
        String textInfo;

        if (!fruit.isUnknown()) {
            textInfo = "The best fruit from " + fruit.getOrigin() + " is " + fruit.getName();
        } else {
            textInfo = "Sorry, we don't have many info about " + fruit.getName();
        }

        Toast.makeText(MainActivity.this, textInfo, Toast.LENGTH_SHORT).show();
    }

    // Menu principal
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflamos el option menu con nuestro layout
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        // Despues de inflar, recogemos las referencias a los botones que nos interesan
        this.itemListView = menu.findItem(R.id.change_to_list);
        this.itemGridView = menu.findItem(R.id.change_to_grid);
        return true;
    }

    //Interaccion menu principal
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Que item se ha seleccionado?
        switch (item.getItemId()) {
            case R.id.add_fruit:
                this.addFruit(new Fruit("Added #" + ++this.counter, R.mipmap.ic_myicon));
                return true;
            case R.id.change_to_grid:
                this.switchListGridView(this.SWITCH_TO_GRID_VIEW);
                return true;
            case R.id.change_to_list:
                this.switchListGridView(this.SWITCH_TO_LIST_VIEW);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Menu contextual (mantener pulsado)
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        // Para sacar el item de la lista, primero debemos saber a cual nos referimos
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        // , y seteamos el titulo de la cabecera del menu:
        menu.setHeaderTitle(this.fruits.get(info.position).getName());

        // E inflamos el menu con el layout creado + el menu arriba modificado
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    // Interaccion menu contextual
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Para sacar el item de la lista, primero debemos saber a cual nos referimos
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        // Que item se ha seleccionado?
        switch (item.getItemId()) {
            case R.id.delete_fruit:
                this.removeFruit(info.position);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


    private void switchListGridView(int option) {
        //Metodo para cambiar entre Grid/List view
        if (option == SWITCH_TO_LIST_VIEW) {
            // Si queremos cambiar a list view, y el list view esta en modo invisible...
            if (this.listView.getVisibility() == View.INVISIBLE) {
                // ... escondemos el grid view, y mostramos su boton en el main menu
                this.gridView.setVisibility(View.INVISIBLE);
                this.itemGridView.setVisible(true);
                // , y mostramos el list view y escondemos su boton del main menu
                this.listView.setVisibility(View.VISIBLE);
                this.itemListView.setVisible(false);
            }
        }
        else if (option == SWITCH_TO_GRID_VIEW) {
            if (this.gridView.getVisibility() == View.INVISIBLE) {
                // ... escondemos el list view, y mostramos su boton en el main menu
                this.listView.setVisibility(View.INVISIBLE);
                this.itemListView.setVisible(true);
                // , y mostramos el grid view y escondemos su boton del main menu
                this.gridView.setVisibility(View.VISIBLE);
                this.itemGridView.setVisible(false);
            }
        }
    }


    // CRUD actions - Get, Add, Delete

    private List<Fruit> getAllFruits() {
        // Primeras frutas a mostrar, hardcodeadas
        return new ArrayList<Fruit>() {{
            add(new Fruit("Banana", "Canarias", R.mipmap.ic_mybanana));
            add(new Fruit("Cucumber", "Fontpineda", R.mipmap.ic_mycucumber));
            add(new Fruit("Apple", "Rioja", R.mipmap.ic_myapple));
            add(new Fruit("Banana", "Canarias", R.mipmap.ic_mybanana));
            add(new Fruit("Cucumber", "Fontpineda", R.mipmap.ic_mycucumber));
            add(new Fruit("Apple", "Rioja", R.mipmap.ic_myapple));
            add(new Fruit("Banana", "Canarias", R.mipmap.ic_mybanana));
            add(new Fruit("Cucumber", "Fontpineda", R.mipmap.ic_mycucumber));
            add(new Fruit("Apple", "Rioja", R.mipmap.ic_myapple));
        }};
    }

    private void addFruit(Fruit fruit) {
        // Anadimos la fruta a la lista
        this.fruits.add(fruit);
        // Refrescamos los adaptadores:
        this.adapterListView.notifyDataSetChanged();
        this.adapterGridView.notifyDataSetChanged();
    }

    private void removeFruit(int position) {
        // Eliminamos la fruta de la lista
        this.fruits.remove(position);
        // Refrescamos los adaptadores:
        this.adapterListView.notifyDataSetChanged();
        this.adapterGridView.notifyDataSetChanged();
    }
}
