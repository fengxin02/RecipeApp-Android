
# Házi feladat dokumentáció

### [Recept ]

<img src="./Recept/app/src/main/res/mipmap-xhdpi/ic_launcher.webp" width="160"/>

## Bemutatás

A program egy receptkönyv alkalmazás, ahol össze vannak gyüjtve a receptek. Nagyon szeretek főzni, és gondoltam milyen menő lenne egy saját recept alkalmazást készíteni. így megszületett az alkalmazás, ez egy tökéletes alkalmazás mindenkinek aki szeret főzni, vagy csak recepteket szeretne nézegetni.

## Főbb funkciók

Az alkalmazás főmenüjében fel vannak sorolva a receptek egy rácsos Grid nézetben, ha valamelyik megtetszik részletesen is meglehet tekinteni az elkészítési módját. A kedvenc recepteidet berakhatod a "kedvencek" listájában, hogy máskor könnyebben lehessen megtalálni. Egy időzítő van beépítve az alkalmazásban, hogy amikor készíted az ételt ne kelljen folyamatosan figyelni az időre.

## Felhasználói kézikönyv

Az alkalmazás indításakor a főképernyő fogadja a felhasználót, ahol alapértelmezetten 'a' beture keresett receptek jelennek meg. Jobb alsó sarokon lévő szívre kattintva lehet kedvencek részletes nézetre navigálni, és  a szív felett lévő csnengőre kattintva lehet időzítőre. 
</p>

<p align="center"> 
<img src="./Recept/assets/homescreen.png" width="320"/>
<br>
1. ábra: A főképernyő felső TopAppBar-nál van keresés, jobb alsó sarokban kedvencek, és időzítő. 
</p>


A lista elemeire (recept) kattintva megjelenik a részletes nézet, ahol a felhasználó megtekintheti az étel hozzávalóit és elkészítési módját. A jobb felső sarokban található szív ikonnal a recept menthető a kedvencek közé, így az internetkapcsolat nélkül is elérhető marad.

<p align="center"> <img src="./Recept/assets/detailscreen.png" width="320"/>
<br>
2. ábra: A recept részletes nézete
</p>


Ha megnyitottad az időzítőtt, akkor ott előre beállított (1, 5, 10 perc) vagy egyéni időtartam adható meg. Az időzítő a háttérben is fut, és értesítésben jelzi a hátralévő időt. Stop Timerre kattintva leállítja az időzítőt.
<p align="center"> <img src="./Recept/assets/timerscreen.png" width="320"/>
<br>
3. ábra: Időzítő részletes nézete
</p>


Ha megnyitottad az kedvenceket, akkor itt látható az összes elmentett kedvenc receptek. Offline is működik.
<p align="center"> <img src="./Recept/assets/favouritescreen.png" width="320"/>
<br>
4. ábra: Kedvencek részletes nézete
</p>


## Felhasznált technológiák:

• **MVVM (Model-View-ViewModel) architektúrában** van készítve a program.

• A felhasználói felület **Jetpack Compose**.

• **Type-Safe Navigation** a képernyők közötti biztonságos navigációhoz.

• **Hilt** a Dependency Injection felhasználása.

• [**Retrofit**](https://square.github.io/retrofit/) és [**Moshi**](https://github.com/square/moshi) a hálózati kommunikációhoz és a JSON feldolgozáshoz.

• [The Meal DB](https://www.themealdb.com/) api felhasználása

• **Room** Persistence könyvtár a kedvenc receptek helyi tárolásához.

• [Coil](https://coil-kt.github.io/coil/) a képek aszinkron betöltéséhez és gyorsítótárazásához.

• **Service**, és **NotificationCompat** használata háttérben futó időzítő megvalósításához.

## Fontosabb technológiai megoldások

A fejlesztés során a nehézség volt az offline adatnak a részletes oldal betöltése. Először úgy próbáltam kikeresni az adatbázisban elmentett adatokat hogy a "getSavedMeals" függvénnyel ami visszaadja az összes ételt, és abból kikeresni az "id" alapján a részletes nézetett, de sajnos nem sikerült. Valószínűleg azért nem működött, mert egy flowt add vissz a függvény. Flownak ugye van késés ezért nem tölt be a részletes adatai. Utána rájottem, hogy adatbázisban tudok csinálni külön műveletett ami kikeresi egyből a kívánt "id" alapú recept részletes adatait. Mivel "id" alapú keresés már egy konkrét "Meal"-t ad vissza, így már működött.

