import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test the StationDefinitionList class and the toString methods for DataSet,
 * DataYear, and DataMonth
 * 
 * @author Nigel, Kyle
 * @version 10/2016
 *
 */
public class StationDefinitionListTest
{
    /**
     * Test all Statistics
     * 
     * @throws IOException
     *             for the file reader
     */
    @Test
    public void testStatistics() throws IOException
    {
        StationDefinitionList list1 =
                new StationDefinitionList("data/geoinfo.csv");
        DataDefinitionList day =
                new DataDefinitionList("data/DataTranslation.csv");
        DataDay.setDataDefinitionList(day);
        list1.loadData("data/alldata_2015.csv");
        KeyConstraints testConstraint = new KeyConstraints();
        testConstraint.add(2015);
        Assert.assertEquals("15.7975",
                list1.getStatisticAverage("TISH", "ATOT", testConstraint)
                        .toString());
        Assert.assertEquals("2015-12-27", list1
                .getStatisticMaxDay("TISH", "WSMX", testConstraint).toString());
        Assert.assertEquals("2015-03-05", list1
                .getStatisticMinDay("TISH", "WMAX", testConstraint).toString());

    }

    /**
     * Test the getMethods and toString methods
     * 
     * @throws IOException
     *             for the file reader
     */
    @Test
    public void testGetMethods() throws IOException
    {
        StationDefinitionList list1 =
                new StationDefinitionList("data/geoinfo.csv");
        DataDefinitionList day =
                new DataDefinitionList("data/DataTranslation.csv");
        DataDay.setDataDefinitionList(day);
        list1.loadData("data/alldata_2015.csv");
        Assert.assertEquals(
                "[ACME, ADAX, ALTU, ALV2, ALVA, ANT2, ANTL, APAC, ARD2, "
                        + "ARDM, ARNE, BBOW, BEAV, BEEX, BESS, BIXB, BLAC, BOI"
                        + "S, "
                        + "BOWL, BREC, BRIS, BROK, BUFF, BURB, BURN, BUTL, BYA"
                        + "R, "
                        + "CALV, CAMA, CARL, CATO, CENT, CHAN, CHER, CHEY, CHI"
                        + "C, "
                        + "CLAR, CLAY, CLOU, CLRM, COOK, COPA, DURA, ELKC, ELR"
                        + "E,"
                        + " ERIC, EUFA, EVAX, FAIR, FITT, FORA, FREE, FTCB, GOO"
                        + "D,"
                        + " GRA2, GRAN, GUTH, HASK, HECT, HINT, HOBA, HOLD, HOL"
                        + "L,"
                        + " HOOK, HUGO, IDAB, INOL, JAYX, KENT, KETC, KIN2, KIN"
                        + "G,"
                        + " LAHO, LANE, MADI, MANG, MARE, MARS, MAYR, MCAL, MED"
                        + "F,"
                        + " MEDI, MIAM, MINC, MRSH, MTHE, NEWK, NEWP, NINN, NOR"
                        + "M,"
                        + " NOWA, NRMN, OILT, OKCE, OKCN, OKCW, OKEM, OKMU, PAU"
                        + "L,"
                        + " PAWN, PERK, PORT, PRES, PRYO, PUTN, REDR, RETR, RIN"
                        + "G,"
                        + " SALL, SEIL, SHAW, SKIA, SLAP, SPEN, STIG, STIL, STU"
                        + "A,"
                        + " SULP, TAHL, TALA, TALI, TIPT, TISH, TULL, TULN, VAL"
                        + "L,"
                        + " VANO, VINI, WAL2, WALT, WASH, WATO, WAUR, WEAT, WEB"
                        + "B,"
                        + " WEBR, WEST, WILB, WIST, WOOD, WYNO]",
                list1.getStationIds().toString());

        Assert.assertEquals("Fittstown FITT: Fittstown",
                list1.getStationInfo("FITT").toString());

        Assert.assertEquals("Acme ACME: Rush Springs\n" + "Ada ADAX: Ada\n"
                + "Altus ALTU: Altus\n" + "Alva ALV2: Alva\n"
                + "Alva ALVA: Alva\n" + "Antlers ANT2: Antlers\n"
                + "Antlers ANTL: Antlers\n" + "Apache APAC: Apache\n"
                + "Ardmore ARD2: Ardmore\n" + "Ardmore ARDM: Ardmore\n"
                + "Arnett ARNE: Arnett\n" + "Broken Bow BBOW: Broken Bow\n"
                + "Beaver BEAV: Beaver\n" + "Bee BEEX: Tishomingo\n"
                + "Bessie BESS: Bessie\n" + "Bixby BIXB: Bixby\n"
                + "Blackwell BLAC: Blackwell\n"
                + "Boise City BOIS: Boise City\n" + "Bowlegs BOWL: Bowlegs\n"
                + "Breckinridge BREC: Breckinridge\n"
                + "Bristow BRIS: Bristow\n" + "Broken Bow BROK: Broken Bow\n"
                + "Buffalo BUFF: Buffalo\n" + "Burbank BURB: Burbank\n"
                + "Burneyville BURN: Burneyville\n" + "Butler BUTL: Butler\n"
                + "Byars BYAR: Byars\n" + "Calvin CALV: Calvin\n"
                + "Camargo CAMA: Camargo\n"
                + "Lake Carl Blackwell CARL: Orlando\n"
                + "Catoosa CATO: Catoosa\n" + "Centrahoma CENT: Centrahoma\n"
                + "Chandler CHAN: Sparks\n" + "Cherokee CHER: Cherokee\n"
                + "Cheyenne CHEY: Cheyenne\n" + "Chickasha CHIC: Chickasha\n"
                + "Claremore CLAR: Claremore\n" + "Clayton CLAY: Clayton\n"
                + "Cloudy CLOU: Cloudy\n" + "Claremore CLRM: Claremore\n"
                + "Cookson COOK: Marble City\n" + "Copan COPA: Copan\n"
                + "Durant DURA: Durant\n" + "Elk City ELKC: Elk City\n"
                + "El Reno ELRE: El Reno\n" + "Erick ERIC: Erick\n"
                + "Eufaula EUFA: Eufaula\n" + "Eva EVAX: Eva\n"
                + "Fairview FAIR: Fairview\n" + "Fittstown FITT: Fittstown\n"
                + "Foraker FORA: Foraker\n" + "Freedom FREE: Freedom\n"
                + "Fort Cobb FTCB: Fort Cobb\n" + "Goodwell GOOD: Goodwell\n"
                + "Grandfield GRA2: Grandfield\n"
                + "Grandfield GRAN: Grandfield\n" + "Guthrie GUTH: Guthrie\n"
                + "Haskell HASK: Haskell\n" + "Hectorville HECT: Hectorville\n"
                + "Hinton HINT: Hinton\n" + "Hobart HOBA: Hobart\n"
                + "Holdenville HOLD: Holdenville\n" + "Hollis HOLL: Gould\n"
                + "Hooker HOOK: Hooker\n" + "Hugo HUGO: Hugo\n"
                + "Idabel IDAB: Idabel\n" + "Inola INOL: Inola\n"
                + "Jay JAYX: Jay\n" + "Kenton KENT: Kenton\n"
                + "Ketchum Ranch KETC: Velma\n"
                + "Kingfisher KIN2: Kingfisher\n"
                + "Kingfisher KING: Kingfisher\n" + "Lahoma LAHO: Lahoma\n"
                + "Lane LANE: Lane\n" + "Madill MADI: Lebanon\n"
                + "Mangum MANG: Mangum\n" + "Marena MARE: Coyle\n"
                + "Marshall MARS: Marshall\n" + "May Ranch MAYR: Freedom\n"
                + "McAlester MCAL: McAlester\n" + "Medford MEDF: Medford\n"
                + "Medicine Park MEDI: Medicine Park\n" + "Miami MIAM: Miami\n"
                + "Minco MINC: Minco\n" + "Marshall MRSH: Marshall\n"
                + "Mt Herman MTHE: Smithville\n" + "Newkirk NEWK: Newkirk\n"
                + "Newport NEWP: Ardmore\n" + "Ninnekah NINN: Ninnekah\n"
                + "Norman NORM: Norman\n" + "Nowata NOWA: Delaware\n"
                + "Norman NRMN: Norman\n" + "Oilton OILT: Oilton\n"
                + "Oklahoma City East OKCE: Oklahoma City\n"
                + "Oklahoma City North OKCN: Oklahoma City\n"
                + "Oklahoma City West OKCW: Oklahoma City\n"
                + "Okemah OKEM: Okemah\n" + "Okmulgee OKMU: Morris\n"
                + "Pauls Valley PAUL: Pauls Valley\n" + "Pawnee PAWN: Pawnee\n"
                + "Perkins PERK: Perkins\n" + "Porter PORT: Clarksville\n"
                + "Preston PRES: Preston\n" + "Pryor PRYO: Adair\n"
                + "Putnam PUTN: Putnam\n" + "Red Rock REDR: Red Rock\n"
                + "Retrop RETR: Willow\n" + "Ringling RING: Ringling\n"
                + "Sallisaw SALL: Sallisaw\n" + "Seiling SEIL: Seiling\n"
                + "Shawnee SHAW: Shawnee\n" + "Skiatook SKIA: Skiatook\n"
                + "Slapout SLAP: Slapout\n" + "Spencer SPEN: Spencer\n"
                + "Stigler STIG: Stigler\n" + "Stillwater STIL: Stillwater\n"
                + "Stuart STUA: Stuart\n" + "Sulphur SULP: Sulphur\n"
                + "Tahlequah TAHL: Tahlequah\n" + "Talala TALA: Talala\n"
                + "Talihina TALI: Talihina\n" + "Tipton TIPT: Tipton\n"
                + "Tishomingo TISH: Tishomingo\n"
                + "Tullahassee TULL: Tullahassee\n" + "Tulsa TULN: Tulsa\n"
                + "Valliant VALL: Valliant\n" + "Vanoss VANO: Vanoss\n"
                + "Vinita VINI: Centralia\n" + "Walters WAL2: Walters\n"
                + "Walters WALT: Walters\n" + "Washington WASH: Washington\n"
                + "Watonga WATO: Watonga\n" + "Waurika WAUR: Waurika\n"
                + "Weatherford WEAT: Weatherford\n"
                + "Webbers Falls WEBB: Webbers Falls\n"
                + "Webbers Falls WEBR: Webbers Falls\n"
                + "Westville WEST: Westville\n" + "Wilburton WILB: Wilburton\n"
                + "Wister WIST: Wister\n" + "Woodward WOOD: Woodward\n"
                + "Wynona WYNO: Wynona\n", list1.toString());

        DataSet dataSet = new DataSet();
        Assert.assertEquals("DataSet: null", dataSet.toString());

        DataYear year = new DataYear();
        Assert.assertEquals("0000, null", year.toString());

        DataMonth month = new DataMonth();
        Assert.assertEquals("0000-00, null", month.toString());

    }

}
