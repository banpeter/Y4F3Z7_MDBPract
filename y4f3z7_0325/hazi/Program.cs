using System;
using System.Linq;
using System.Xml.Linq;

// A bolt.xml betöltése
XDocument dokumentum = XDocument.Load("bolt.xml");
XElement gyoker = dokumentum.Descendants("bolt").First();


// 0. A teljes dokumentum
Console.WriteLine("(0.) A teljes dokumentum:\n\n" + gyoker);


// 1. 
Console.WriteLine("(1.) 24 hónapos garanciájú áruk:\n");
var hosszuGaranciasAruk = gyoker.Descendants("aru")
    .Where(elem => elem.Descendants("garancia_honap").First().Value == "24")
    .ToList();
hosszuGaranciasAruk.ForEach(elem =>
    Console.WriteLine(" - " + elem.Descendants("nev").First().Value));


// 2. 
Console.WriteLine("\n(2.) Melyik ügyfél, mit rendelt, mennyiért:\n");
var harmasJoin = gyoker.Descendants("rendeles")
    .Select(rendeles => {
        var ugyfelID = rendeles.Attribute("ugyfel_ref").Value;
        var ugyfel = gyoker.Descendants("ugyfel")
            .First(u => u.Attribute("cid").Value == ugyfelID)
            .Descendants("nev")
            .FirstOrDefault().Value;

        var aruID = rendeles.Descendants("tetel").First().Attribute("aru_ref").Value;
        var aru = gyoker.Descendants("aru")
            .First(a => a.Attribute("aid").Value == aruID)
            .Descendants("nev")
            .FirstOrDefault().Value;

        var osszeg = rendeles.Descendants("osszeg").First().Value;

        return new {
            Ugyfel  = ugyfel,
            Aru     = aru,
            Osszeg  = osszeg
        };
    })
    .ToList();

harmasJoin.ForEach(sor =>
    Console.WriteLine($" - {sor.Ugyfel} rendelte: {sor.Aru}, összeg: {sor.Osszeg} Ft"));


// 3. 
var atlagKoltes = gyoker.Descendants("rendeles")
    .Select(r => r.Descendants("osszeg").First().Value)
    .Average(osszeg => double.Parse(osszeg));
Console.WriteLine($"\n(3.) Az átlagos rendelési összeg: {atlagKoltes} Ft");


// 4. 
Console.WriteLine("\n(4.) Minden rendelés összegét megduplázom, majd elmentem:\n");
gyoker.Descendants("rendeles")
    .ToList()
    .ForEach(rendeles => {
        var osszegElem = rendeles.Descendants("osszeg").First();
        var osszeg = double.Parse(osszegElem.Value);
        osszeg *= 2;
        osszegElem.Value = osszeg.ToString();
    });
XDocument modositottDokumentum = new XDocument(gyoker);
modositottDokumentum.Save("bolt_modositott.xml");
Console.WriteLine("Az új fájl neve: \"bolt_modositott.xml\"");


// 5. 
Console.WriteLine("\n(5.) Törlöm a szervizben lévő járműveket, majd elmentem:\n");
gyoker.Descendants("jarmu")
    .Where(j => j.Descendants("szervizben").First().Value == "true")
    .ToList()
    .ForEach(j => j.Remove());

XDocument toroltDokumentum = new XDocument(gyoker);
toroltDokumentum.Save("bolt_torolt.xml");
Console.WriteLine("Az új fájl neve: \"bolt_torolt.xml\"");


// 6. 
Console.WriteLine("\n(6.) Egy új XML dokumentum létrehozása:\n");
XElement ujGyoker = new XElement("vallalat",
    new XElement("alkalmazott",
        new XAttribute("id", "emp1"),
        new XElement("nev", "Kovács Béla"),
        new XElement("osztaly", "IT"),
        new XElement("fizetes", "500000")
    ),
    new XElement("alkalmazott",
        new XAttribute("id", "emp2"),
        new XElement("nev", "Tóth Mária"),
        new XElement("osztaly", "HR"),
        new XElement("fizetes", "420000")
    )
);

// LINQ: minden alkalmazott fizetését megemelem 10%-kal, és hozzáadom a beosztást
ujGyoker.Descendants("alkalmazott")
    .ToList()
    .ForEach(alk => {
        var fizetesElem = alk.Descendants("fizetes").First();
        var fizetes = double.Parse(fizetesElem.Value);
        fizetes *= 1.1;
        fizetesElem.Value = ((int)fizetes).ToString();

        alk.Add(new XElement("beosztas", "junior"));
    });

XDocument ujDokumentum = new XDocument(ujGyoker);
ujDokumentum.Save("vallalat.xml");
Console.WriteLine("Az új fájl neve: \"vallalat.xml\"");