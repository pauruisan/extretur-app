package com.uax.extretur.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationBarView
import com.uax.extretur.R
import com.uax.extretur.adapters.AdaptadorGastro
import com.uax.extretur.databinding.ActivityGastroBinding
import com.uax.extretur.model.Gastro
import com.google.firebase.auth.FirebaseAuth

class GastroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGastroBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var listaGastro: ArrayList<Gastro>
    private lateinit var adaptadorGastro: AdaptadorGastro

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navGastro.navLayout.setOnItemSelectedListener(object :
            NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                return when (item.itemId) {
                    binding.navGastro.navLayout.menu.findItem(R.id.inicio).itemId -> {
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                        true
                    }

                    binding.navGastro.navLayout.menu.findItem(R.id.foro).itemId -> {
                        val user = auth.currentUser

                        if (user == null) {
                            val intent = Intent(applicationContext, LogInActivity::class.java)
                            startActivity(intent)
                        } else {
                            val intent = Intent(applicationContext, ForumActivity::class.java)
                            startActivity(intent)
                        }
                        true
                    }

                    binding.navGastro.navLayout.menu.findItem(R.id.perfil).itemId -> {
                        val user = auth.currentUser

                        if (user == null) {
                            val intent = Intent(applicationContext, LogInActivity::class.java)
                            startActivity(intent)
                        } else {
                            val intent = Intent(applicationContext, ProfileActivity::class.java)
                            startActivity(intent)
                        }
                        true
                    }

                    else -> false
                }
            }
        })

        instancias()
        acciones()
    }


    private fun instancias() {
        listaGastro = arrayListOf(
            Gastro(
                "Migas extremeñas",
                "Tradicional plato de origen pastoril, muy humilde pero sabroso. Se elabora a base de pan del día anterior desmenuzado, que se sofríe con ajo, panceta, chorizo y pimentón de la Vera. A menudo se acompaña con uvas, higos o pimientos fritos, dependiendo de la zona. Es muy común en otoño e invierno y suele servirse como desayuno contundente en días fríos o como plato principal en reuniones familiares y fiestas populares. " +
                        "\nDónde comerlas: \n- Restaurante La Tojera (Valencia de Alcántara, Cáceres): tradicionales y generosas.\n" +
                        "- Mesón El Lebrillo (Mérida): buena ración y ambiente acogedor.\n  ",
                "https://recetasdecocina.elmundo.es/wp-content/uploads/2024/11/migas-extremenas-receta-1024x683.jpg"
            ),
            Gastro(
                "Torta del Casar",
                "Queso artesano con Denominación de Origen Protegida, elaborado con leche cruda de oveja merina. Su característica principal es su textura cremosa y fundente, tanto que se abre la tapa superior del queso y se toma con cuchara o pan. Tiene un sabor fuerte, ligeramente amargo y muy aromático debido al cuajo vegetal (flor del cardo) utilizado. Es uno de los quesos más valorados de España." +
                        "\nDónde comerla:\n- Quesería Doña Francisca (Casar de Cáceres): puedes probarla y comprarla directamente.\n" +
                        "- Restaurante Atrio (Cáceres): alta cocina con productos extremeños.",
                "https://www.guiarepsol.com/content/dam/repsol-guia/contenidos-imagenes/comer/cultura-gastronomica/queso-torta-del-casar/gr-cms-media-filer_public-db-7e-db7ea048-69f6-4b5f-b563-e2f0cb65093c-5.jpg.transform/rp-rendition-sm/image.jpg"
            ),
            Gastro(
                "Caldereta de cordero",
                "Plato tradicional de celebraciones y reuniones del campo. El cordero se guisa lentamente con ajo, cebolla, laurel, pimentón y vino blanco o vinagre. El resultado es una carne tierna y jugosa, con una salsa intensa. A veces se acompaña de patatas o pan para mojar. Suele prepararse en grandes cantidades en calderos al aire libre durante romerías y fiestas." +
                        "\nDónde comerla:\n- Asador El Figón de Eustaquio (Cáceres): caldereta muy bien valorada.\n" +
                        "- La Dehesa (Zafra): buen producto y trato familiar.",
                "https://www.saboraextremadura.es/wp-content/uploads/2018/04/caldereta-extreme%C3%B1a.jpg"
            ),
            Gastro(
                "Jamón ibérico de bellota",
                "El jamón ibérico es uno de los productos más emblemáticos de la gastronomía española, y Extremadura cuenta con una de las zonas de producción más prestigiosas gracias a su entorno natural: la Dehesa." +
                        "\nEl jamón extremeño está protegido por la D.O.P. Dehesa de Extremadura, lo que garantiza su origen, calidad y método de elaboración artesanal. Solo los jamones que cumplen con los estrictos criterios de esta denominación pueden llevar su sello." +
                        "\nDónde comerlo: \n- Casa Claudio (Zafra): tienen una excelente bodega de ibéricos y cortadores expertos.\n- Jamones Peña Cruz (Monesterio): también ofrecen visitas y degustaciones guiadas.\n" +
                        "" +
                        "- Museo del Jamón (Monesterio): ideal para aprender más y probar distintas calidades.",
                "https://dehesa-extremadura.com/wp-content/uploads/2025/03/BG-jamon-copia.jpg"
            ),
            Gastro(
                "Zorongollo",
                "El zorongollo es una ensalada templada típica del norte de Extremadura, especialmente de la comarca de La Vera. Se elabora con pimientos rojos asados al horno o a la brasa, a los que se añade ajo picado, sal, buen aceite de oliva y a veces tomate. Servido con huevos fritos, forma un plato simple pero lleno de sabor. También es habitual encontrarlo con bacalao o como guarnición.\n Dónde probarlo:" +
                        "\n- Mesón La Troya (Trujillo): cocina típica y buen zorongollo.\n" +
                        "- La Cacharrería (Cáceres): versión moderna con productos de la zona.",
                "https://dextremaduralomejor.es/images/zorongollo.jpg"
            ),
            Gastro(
                "Bacalao dorado",
                "Versión extremeña del “bacalhau à Brás” portugués, heredado de la cercanía con Portugal. Lleva bacalao desalado desmigado, salteado con cebolla pochada, huevo batido y patatas paja. \nEl resultado es una mezcla jugosa y sabrosa. Se suele decorar con perejil y aceitunas negras. Plato rápido, económico y muy apreciado en fiestas familiares o como tapa.\n Dónde comerlo:\n" +
                        "- O Moinho (Valencia de Alcántara): fusión luso-extremeña.\n" +
                        "- El Alma del Sabor (Badajoz)",
                "https://cdn.recetasderechupete.com/wp-content/uploads/2022/11/Bacalao-dourado-portugues-casero-portada.jpg"
            ),
            Gastro(
                "Repápalos",
                "Los repápalos son un plato tradicional de la cocina de aprovechamiento de Extremadura, especialmente popular en la provincia de Cáceres. Se trata de unas albóndigas elaboradas con pan duro, huevo, ajo y perejil, que se fríen y luego se cocinan en una salsa sencilla a base de ajo, cebolla, pimentón y laurel. Este plato, humilde pero sabroso, refleja la esencia de la gastronomía rural extremeña, donde nada se desperdicia y se aprovechan al máximo los ingredientes disponibles. \nDónde comerlo:\n" +
                        "- Restaurante Tapería Ibérico (Cáceres): conocido por su cocina tradicional extremeña, es uno de los lugares donde se pueden encontrar repápalos en su menú..\n" +
                        "- El Figón de Eustaquio (Cáceres): Uno de los restaurantes más emblemáticos de la ciudad, conocido por su cocina creativa que fusiona sabores tradicionales.",
                "https://i.blogs.es/9993fa/repapalos-extremenos/450_1000.jpg"
            ),
            Gastro(
                "Pestorejo",
                "El pestorejo es un plato emblemático de la gastronomía extremeña, especialmente popular en la provincia de Badajoz. Se elabora a partir de la careta del cerdo, que incluye el morro, la jeta y las orejas. Esta parte del cerdo se adoba con ajo, pimentón de la Vera, perejil y sal, y luego se cocina a la brasa o al horno hasta que la piel queda crujiente y la carne tierna. Es un plato que refleja la tradición de la matanza y el aprovechamiento integral del cerdo en la cocina rural extremeña. \nDónde probarlo:\n" +
                        "- Mesón El Pestorejo (Mérida): este establecimiento es conocido por su especialidad en pestorejo, ofreciendo una preparación tradicional a la brasa que ha recibido buenas críticas por su sabor auténtico. \n" +
                        "- Don Pestorejo (Don Benito):  ofrece pestorejo en su menú, siendo una opción para quienes deseen probar este plato en la zona..",
                "https://dextremaduralomejor.es/images/pestorejo-extremeno.jpg"
            ),
            Gastro(
                "Morcilla patatera",
                "Embutido único de Extremadura, elaborado con patata cocida, grasa de cerdo y pimentón, lo que le da su característico color rojizo. Existen dos variedades: dulce (más suave) y picante. Se puede comer cruda, untada en pan como aperitivo, o cocinada (por ejemplo, en revueltos o a la plancha). Es típica del carnaval y otras festividades rurales. Dónde probarla:\n" +
                        "- Carnicería Hnos. Gallardo (Cáceres): tradicional y sabrosa.\n" +
                        "- Tapería Yuste (Jaraíz de la Vera): también hacen tapas con ella.",
                "https://imagenes.elpais.com/resizer/v2/O2LWL63QI5MU7AD7FMGKFHALLU.jpg?auth=8b695cdf7e07ae41c8fdeecde8663d294ec7bffefc23f17bc22d9f3a8a5f56ef&width=414"
            ),
            Gastro(
                "Gazpacho extremeño",
                "Versión local del famoso gazpacho andaluz, aunque con diferencias: es más espeso, con más pan, y suele llevar más ajo y pimiento. Refrescante, se toma en verano y se acompaña de guarniciones como pepino, huevo duro o trocitos de jamón. En algunas zonas se sirve con uvas o se le añade vinagre de vino más fuerte. Dónde probarlo:\n" +
                        "- Mesón Castellano (Zafra): fresco y casero.\n" +
                        "- La Minerva (Cáceres): cocina de mercado.",
                "https://imagenes.20minutos.es/files/image_990_556/uploads/imagenes/2020/06/07/gazpacho.jpeg"
            ),
            Gastro(
                "Escabeche de peces del río",
                "Tradición muy arraigada en zonas fluviales (como La Vera o Tierra de Barros). Se preparan peces como la tenca, barbo o carpa, primero fritos y luego cocinados en vinagre con laurel, ajo y pimentón. Este método los conserva durante días y realza su sabor. Se sirve frío o templado, y es típico de ferias y fiestas populares cerca de ríos o embalses. Dónde comerlo:\n" +
                        "- Restaurante El Mirador de la Portilla del Tiétar (Monfragüe): producto local y de temporada.\n" +
                        "- Casa Rural El Lago (Losar de la Vera): suele estar en el menú.",
                "https://s3.ppllstatics.com/hoy/www/multimedia/2023/05/09/peces-engazpachados-RhWnNaqnVe5R7OUG8qug6aO-1200x840@Hoy.jpg"
            ),
            Gastro(
                "Chanfaina",
                "Plato de origen árabe y con fuerte componente pastoril. Es un guiso de arroz con casquería de cordero (hígado, pulmón, sangre cocida), condimentado con ajo, pimentón, laurel y a veces comino. Tiene un sabor muy intenso y profundo. Aunque no gusta a todos por sus ingredientes, es una joya culinaria para los amantes de la cocina tradicional. Es protagonista de fiestas gastronómicas como la de Fuente de Cantos. Dónde probarla:\n" +
                        "- Fiesta de la Chanfaina (Fuente de Cantos, último domingo de abril): fiesta gastronómica popular de interés turístico regional.\n" +
                        "- Bar El Poli (Fuente de Cantos): también la tienen durante el año.",
                "https://imag.bonviveur.com/chanfaina-extremena-decorada-con-perejil.jpg"
            ),
            Gastro(
                "Perrunillas",
                "Dulces típicos de manteca de cerdo, harina, azúcar y huevo. Tienen una textura arenosa y se deshacen en la boca. Suelen llevar un poco de azúcar espolvoreada por encima y a veces un toque de anís. Muy presentes en Navidad y Semana Santa, aunque se encuentran todo el año en pastelerías tradicionales. Dónde comprarlas:\n" +
                        "- Pastelería Isa (Trujillo): muy tradicionales.\n" +
                        "- Pastelería Paniagua (Cáceres): muy buena repostería local.\n",
                "https://www.dulceselcapricho.com/65-tm_thickbox_default/perrunillas-artesanas-extremenas.jpg"
            ),
            Gastro(
                "Técula mécula",
                "Delicia dulce de influencia árabe-portuguesa, tradicional de Olivenza. Su base es una pasta de almendras, azúcar, yema de huevo y manteca, que forma una tarta compacta y brillante. Muy energética y sabrosa, se ha convertido en un regalo gourmet. Su nombre proviene del latín vulgar y se traduce como “esto para ti, aquello para mí”. Dónde comerlo:\n" +
                        "- Confitería Vicente Plana (Olivenza): es el sitio más tradicional.\n" +
                        "- Pastelería La Rosa (Badajoz): excelente versión del postre.",
                "https://guiarepsol.com//content/dam/repsol-guia/contenidos-imagenes/comer/en-el-mercado/t%C3%A9cula-m%C3%A9cula-(olivenza)/10Tecula_Mecula_.jpg.transform/rp-rendition-xs/image.jpg"
            ),
            Gastro(
                "Revuelto de criadillas de tierra",
                "Las criadillas de tierra, también llamadas trufas del desierto o terfecias, son hongos que crecen de forma silvestre en primavera en suelos arenosos. Tienen un sabor terroso, intenso y muy especial. Se preparan principalmente en revuelto con huevos y ajo, aunque también se guisan. Son un producto muy apreciado en la cocina de temporada en el norte de Cáceres y Badajoz. Dónde comerlo:\n" +
                        "- Restaurante Versátil (Zarza de Granadilla): con estrella Michelin, cocina con producto local.\n" +
                        "- La Bodega de la Catedral (Plasencia): temporada de primavera.\n",
                "https://dextremaduralomejor.es/images/criadillas-al-ajillo.jpg"
            ),
        )
        listaGastro.sortBy { it.nombre }

        adaptadorGastro = AdaptadorGastro(listaGastro, this)

        if (resources.configuration.orientation == 1) {
            binding.gastroCards.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        } else if (resources.configuration.orientation == 2) {
            binding.gastroCards.layoutManager = GridLayoutManager(this, 2)
        }
        binding.gastroCards.adapter = adaptadorGastro

    }

    private fun acciones() {
        binding.editSearchGastro.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val textoBuscado = s.toString().lowercase()
                val listaFiltrada: ArrayList<Gastro> = listaGastro.filter {
                    it.nombre.lowercase().contains(textoBuscado) || it.descripcion.lowercase()
                        .contains(textoBuscado)
                }.sortedBy { it.nombre }.toCollection(ArrayList())
                if (listaFiltrada.isEmpty()){
                    Toast.makeText(this@GastroActivity, "No se encontraron resultados", Toast.LENGTH_SHORT).show()
                }
                adaptadorGastro.actualizarLista(listaFiltrada)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }



}