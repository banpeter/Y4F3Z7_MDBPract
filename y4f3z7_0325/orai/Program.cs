using System;
using System.Linq;
using System.Xml.Linq;

XDocument dokumentum = XDocument.Load("gyakorlat.xml");
XElement gyoker = dokumentum.Descendants("vendeglatas").First();


// 0. A teljes dokumentum
Console.WriteLine("(0.) A teljes dokumentum:\n\n" + gyoker);


// 1. Ötcsillagos éttermek szűrése
Console.WriteLine("\n(1.) Az ötcsillagos éttermek:\n");
var otCsillagosEttermek = gyoker.Descendants("etterem")
    .Where(elem => elem.Descendants("csillag").First().Value == "5")
    .ToList();
otCsillagosEttermek.ForEach(elem =>
    Console.WriteLine(" - " + elem.Descendants("nev").First().Value));


// 2. Melyik szakács, melyik étteremben dolgozik (join az e_sz attribútumon)
Console.WriteLine("\n(2.) Melyik szakács, melyik étteremben dolgozik:\n");
var szakacsJoin = gyoker.Descendants("szakacs")
    .Select(szakacs => {
        var etteremID = szakacs.Attribute("e_sz").Value;

        // Megkeressük az étterem nevét az ekod alapján
        var etteremNev = gyoker.Descendants("etterem")
            .FirstOrDefault(e => e.Attribute("ekod").Value == etteremID)
            ?.Descendants("nev").FirstOrDefault()?.Value ?? "Ismeretlen étterem";

        var szakacsNev = szakacs.Descendants("nev").First().Value.Trim();
        var reszleg = szakacs.Descendants("reszleg").First().Value;

        return new {
            Szakacs  = szakacsNev,
            Etterem  = etteremNev,
            Reszleg  = reszleg
        };
    })
    .ToList();

szakacsJoin.ForEach(sor =>
    Console.WriteLine($" - {sor.Szakacs} | Étterem: {sor.Etterem} | Részleg: {sor.Reszleg}"));


// 3. Átlagos életkor (vendégek)
var atlagEletkor = gyoker.Descendants("vendeg")
    .Select(v => v.Descendants("eletkor").First().Value)
    .Average(kor => double.Parse(kor));
Console.WriteLine($"\n(3.) A vendégek átlagos életkora: {atlagEletkor} év");


// 4. Minden szakács életkorát megnövelem 1-gyel, majd elmentem
Console.WriteLine("\n(4.) Minden szakács életkorát növelem 1-gyel, majd elmentem:\n");
gyoker.Descendants("szakacs")
    .ToList()
    .ForEach(szakacs => {
        var korElem = szakacs.Descendants("eletkor").First();
        var kor = int.Parse(korElem.Value);
        kor += 1;
        korElem.Value = kor.ToString();
    });

XDocument modositottDokumentum = new XDocument(gyoker);
modositottDokumentum.Save("vendeglatas_modositott.xml");
Console.WriteLine("Az új fájl neve: \"vendeglatas_modositott.xml\"");


// 5. Törlöm a 4 csillagos éttermeket, majd elmentem
Console.WriteLine("\n(5.) Törlöm a 4 csillagos éttermeket, majd elmentem:\n");
gyoker.Descendants("etterem")
    .Where(elem => elem.Descendants("csillag").First().Value == "4")
    .ToList()
    .ForEach(elem => elem.Remove());

XDocument toroltDokumentum = new XDocument(gyoker);
toroltDokumentum.Save("vendeglatas_torolt.xml");
Console.WriteLine("Az új fájl neve: \"vendeglatas_torolt.xml\"");


// 6. Új XML dokumentum létrehozása (két új vendég)
Console.WriteLine("\n(6.) Egy új XML dokumentum létrehozása:\n");
XElement ujGyoker = new XElement("vendeglatas",
    new XElement("vendeg",
        new XAttribute("vkod", "v10"),
        new XElement("nev", "Teszt Elek"),
        new XElement("eletkor", "30"),
        new XElement("cim",
            new XElement("varos", "Debrecen"),
            new XElement("utca", "Piac utca"),
            new XElement("hazszam", "1")
        )
    ),
    new XElement("vendeg",
        new XAttribute("vkod", "v11"),
        new XElement("nev", "Minta Béla"),
        new XElement("eletkor", "45"),
        new XElement("cim",
            new XElement("varos", "Pécs"),
            new XElement("utca", "Király utca"),
            new XElement("hazszam", "7")
        )
    )
);

// LINQ: minden vendég életkorát megnövelem 5-tel, és hozzáadom a VIP státuszt
ujGyoker.Descendants("vendeg")
    .ToList()
    .ForEach(vendeg => {
        var korElem = vendeg.Descendants("eletkor").First();
        var kor = int.Parse(korElem.Value);
        kor += 5;
        korElem.Value = kor.ToString();

        vendeg.Add(new XElement("statusz", "VIP"));
    });

XDocument ujDokumentum = new XDocument(ujGyoker);
ujDokumentum.Save("vendeglatas_uj.xml");
Console.WriteLine("Az új fájl neve: \"vendeglatas_uj.xml\"");