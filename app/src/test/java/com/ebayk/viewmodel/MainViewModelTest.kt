package com.ebayk.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ebayk.getOrAwaitValues
import com.ebayk.model.ApartmentInfoLoadingStatus
import com.ebayk.model.dto.*
import com.ebayk.usecase.RetrieveApartmentInfo
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import java.lang.Exception

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private val retrieveApartmentInfoMock = mockk<RetrieveApartmentInfo>()
    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `view model created, retrieveApartmentInfo returns data, live data value is first loading, then success`() {
        coEvery { retrieveApartmentInfoMock() } returns testApartmentInfo

        runTest {
            viewModel = MainViewModel(retrieveApartmentInfoMock)

            val values = viewModel.apartmentInfo.getOrAwaitValues(2)

            Assert.assertEquals(ApartmentInfoLoadingStatus.Loading::class, values[0]::class)
            Assert.assertEquals(ApartmentInfoLoadingStatus.Success::class, values[1]::class)
            Assert.assertEquals(testApartmentInfo, (values[1] as ApartmentInfoLoadingStatus.Success).data)
        }
    }

    @Test
    fun `view model created, retrieveApartmentInfo throws error, live data value is first loading, then error`() {
        coEvery { retrieveApartmentInfoMock() } throws Exception()

        runTest {
            viewModel = MainViewModel(retrieveApartmentInfoMock)

            val values = viewModel.apartmentInfo.getOrAwaitValues(2)

            Assert.assertEquals(ApartmentInfoLoadingStatus.Loading::class, values[0]::class)
            Assert.assertEquals(ApartmentInfoLoadingStatus.Error::class, values[1]::class)
        }
    }

    companion object {
        val testApartmentInfo = ApartmentDetails(
            title = "Geräumige Dachgeschoss-Wohnung mit großer Sonnenterasse",
            price = Price(
                amount = 859976,
                currency = "EUR",
            ),
            address = Address(
                "Weserstraße 15",
                "Berlin",
                "12047",
                "13.4285519",
                "52.4874554",
            ),
            postDate = "2021-10-08T08:01:00.000+0100",
            visits = 2869,
            id = "1118635128",
            description = "Objektbeschreibung\nDas Gebäude\n\nDas zeitlos-schlichte Gebäude mit seinen hohen Decken, einladenden Dielenböden und stilvollen Stuck-Elementen ist ein perfektes Beispiel für die unvergängliche Schönheit von Altbauten. Das um 1900 errichtete Objekt besteht ganz klassisch aus einem Vorderhaus, einem Seitenflügel und einem angeschlossenen Quergebäude. Alle Wohnungen sind zudem mit einem Balkon ausgestattet, von dem aus man entspannt die Neuköllner Kiez-Atmosphäre genießen kann. Erst kürzlich wurde das Gebäude modernisiert, sodass der Eingangsbereich, Treppenhäuser, sowie die Fassade und der Innenhof nun in neuem Glanz erstrahlen.\n\nDie Wohnung\n\nDachgeschoss-Wohnungen sind äußerst begehrt und selten. Erst recht, wenn sie in frisch renoviertem Glanz erstrahlen und mit solch einer großen Dachterrasse punkten wie diese 3-Zimmer-Dachgeschoss-Wohnung mit rund 107 Quadratmetern. Dazu eine der angesagtesten Nachbarschaften von Neukölln direkt vor der Haustür. Mit der Sonne als ständiger Gast erleben Sie hier viel Freude. Neben einem großen Wohnzimmer mit Zugang zur schönen Dachterrasse, verfügt die Wohnung über zwei weitere Zimmer, die Sie vom repräsentativen Flur aus betreten. Der Umfang der Renovierung umfasst neue Anstriche von Wänden und Decken, neue Türen in allen Zimmern, neuer Parkettboden, neue Fliesen in der Küche und ein komplett neues Bad. Lassen Sie sich diese besondere Dachgeschoss-Wohnung nicht entgehen.\n\nDie Lage\n\nNeukölln ist einer der buntesten und spannendsten Bezirke Berlins. Besonders der nördliche Teil mit seiner erwachenden Kulturszene hat sich in den letzten Jahren herausgeputzt und dabei enorm vom angrenzenden Kreuzberg profitiert. Das sogenannte Kreuzkölln, in dem sich auch der Reuterkiez befindet, ist eine attraktive Wohngegend mit unzähligen schönen Gründerzeit-Altbauten. Typisch für diese Lage sind die vielen Szenekneipen, Galerien, Cafés und Läden. Vor allem junge, gut ausgebildete Menschen, Künstler, Kreative und Familien zieht es hierher. Viele Möglichkeiten der Naherholung befinden sich zudem in unmittelbarer Umgebung: das Maybachufer, der Volkspark Hasenheide und das Tempelhofer Feld. Alles zusammen, das Image, die Urbanität und auch die zentrale Lage, macht Kreuzkölln zum Magneten für ein junges, dynamisches Publikum.\n\nAusstattung\nGründerzeit-Altbau der Jahrhundertwende\n\nUmfassend neu renoviert\n\nGroße Dachterrasse\n\nHohe Decken\n\nÄußerst nachgefragte Lage\n\nWeitere Angaben\nVerfügbar ab: Bezugsfrei Objektzustand: gepflegt Bodenbelag: Parkett\n\nLagebeschreibung\nNeukölln ist einer der buntesten und spannendsten Bezirke Berlins. Besonders der nördliche Teil mit seiner erwachenden Kulturszene hat sich in den letzten Jahren herausgeputzt und dabei enorm vom angrenzenden Kreuzberg profitiert. Das sogenannte Kreuzkölln, in dem sich auch der Reuterkiez befindet, ist eine attraktive Wohngegend mit unzähligen schönen Gründerzeit-Altbauten. Typisch für diese Lage sind die vielen Szenekneipen, Galerien, Cafés und Läden. Vor allem junge, gut ausgebildete Menschen, Künstler, Kreative und Familien zieht es hierher. Viele Möglichkeiten der Naherholung befinden sich zudem in unmittelbarer Umgebung: das Maybachufer, der Volkspark Hasenheide und das Tempelhofer Feld. Alles zusammen, das Image, die Urbanität und auch die zentrale Lage, macht Kreuzkölln zum Magneten für ein junges, dynamisches Publikum.\n\nSonstiges\nTraumwohnung gefunden? Dann nutzen Sie jetzt unsere eigene Immobilienfinanzierungsberatung und profitieren Sie von jahrelanger Expertise in Verbindung mit kompetenter Beratung. Kontaktieren Sie uns unter +49 (0) 30 220 130 508 oder auf www.ziegert-bank.de und erhalten Sie auf Ihre Bedürfnisse zugeschnittene Angebote.\n\nEnergie\nEnergieausweis: Energieverbrauchsausweis Energieverbrauch: 139.3 kWh(m²*a) Letzte Modernisierung: 2021 Heizungsart: Zentralheizung Wesentliche Energieträger: Gas\n\nAnbieter-Objekt-ID: 237f75e0-1fc1-4bc0-bb41-a26c591f4029",
            attributes = listOf(
                Attribute(
                    label = "Wohnfläche",
                    value = "106,68",
                    unit = "m²",
                ),
                Attribute(
                    label = "Zimmer",
                    value = "3",
                    unit = "",
                ),
                Attribute(
                    label = "Etage",
                    value = "5",
                    unit = "",
                ),
                Attribute(
                    label = "Wohnungstyp",
                    value = "Dachgeschosswohnung",
                ),
                Attribute(
                    label = "Baujahr",
                    value = "1900",
                ),
                Attribute(
                    label = "Provision",
                    value = "Keine zusätzliche Keine zusätzliche Keine zusätzliche",
                    unit = "",
                ),
            ),
            features = listOf(
                "Balkon",
                "Garage / Stellplatz / Stellplatz",
                "Dusche",
                "Aufzug",
                "Keller",
            ),
            pictures = listOf(
                "https://i.ebayimg.com/00/s/MTA2NlgxNjAw/z/~CoAAOSwjZJhSgCC/\$_{imageId}.JPG",
                "https://i.ebayimg.com/00/s/MTA2NlgxNjAw/z/21YAAOSwYEFhSgCC/\$_{imageId}.JPG",
                "https://i.ebayimg.com/00/s/MTA2NlgxNjAw/z/9NEAAOSw6xNhSgCC/\$_{imageId}.JPG",
                "https://i.ebayimg.com/00/s/MTA2NlgxNjAw/z/w4sAAOSwYRJhSgCD/\$_{imageId}.JPG",
                "https://i.ebayimg.com/00/s/MTA2NlgxNjAw/z/zMcAAOSwQaNhSgCC/\$_{imageId}.JPG",
                "https://i.ebayimg.com/00/s/MTA2NlgxNjAw/z/IhEAAOSwdF5hSgCC/\$_{imageId}.JPG",
                "https://i.ebayimg.com/00/s/MTA2NlgxNjAw/z/FoQAAOSw9bVhSgCB/\$_{imageId}.JPG",
                "https://i.ebayimg.com/00/s/MTA2NlgxNjAw/z/LukAAOSwZEFhSgCC/\$_{imageId}.JPG",
            ),
            documents = listOf(
                Document(
                    link = "https://ebay-kleinanzeigen-user-content.de/dokumente/e89b8999-0d1f-4447-9da9-01a0f7d8dfba.pdf",
                    title = "Grundriss",
                ),
                Document(
                    link = "https://ebay-kleinanzeigen-user-content.de/dokumente/2972ace9-271f-4b7c-b55b-4d3e4d6538bd.pdf",
                    title = "Energieausweis Energieausweis Energieausweis",
                ),
            ),
        )
    }
}