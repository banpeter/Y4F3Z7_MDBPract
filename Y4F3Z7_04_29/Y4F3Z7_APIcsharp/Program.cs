using MongoDB . Driver ;
using MongoTest . Models ;

class Program
{
    static void Main ( string [] args )
    {
    var client = new MongoClient (
    " mongodb + srv :// asd : asd@cluster0.zewoi03.mongodb.net/" );
    var database = client . GetDatabase ( " vendeglatas " ) ;
    var etteremCollection =
    database . GetCollection < Etterem >( " ettermek " ) ;
    var foszakacsCollection = database . GetCollection < Foszakacs >( "foszakacsok " ) ;

    }
}


var ettermek = etteremCollection . Find ( _ = > true ) . ToList ()

foreach ( var e in ettermek )
{
    Console . WriteLine ( " ------- " ) ;
    Console . WriteLine ( $ " N v : { e . nev } " ) ;
    Console . WriteLine ( $ " V r o s : { e . cim ?. varos } " ) ;
    Console . WriteLine ( $ " Utca : { e . cim ?. utca } " ) ;
    Console . WriteLine ( $ " H z s z m : { e . cim ?. hazszam } " ) ;
    Console . WriteLine ( $ " Csillag : { e . csillag } " ) ;
}


var foszakacsok = foszakacsCollection . Find ( _ = > true ) .
ToList () ;

foreach ( var f in foszakacsok )
{
    Console . WriteLine ( " ------- " ) ;
    Console . WriteLine ( $ " N v : { f . nev } " ) ;
    Console . WriteLine ( $ " letkor : { f . eletkor } " ) ;
    Console . WriteLine ( $ " Fkod : { f . _fkod } " ) ;
    Console . WriteLine ( $ " EF : { f . _e_f } " ) ;

    Console . WriteLine ( " V g z e t t s g : " ) ;
foreach ( var v in f . vegzettseg )
{
    Console . WriteLine ( " - " + v ) ;
}

}


//////////////////////////////////////////////////
var ujEtterem = new Etterem
{
    nev = " Valhalla " ,
    cim = new Cim
    {
        varos = " N y r e g y h z a " ,
        utca = " Sas " ,
        hazszam = 3
    },
    csillag = 5
};
etteremCollection . InsertOne ( ujEtterem ) ;
Console . WriteLine ( " Sikeres b e s z u r s ! " ) ;




//////////////////////////////////////////////////
var foszakacsCollection = database . GetCollection <
Foszakacs >( " foszakacsok " ) ;


 var ujFoszakacs = new Foszakacs
{
nev = " H e g e d s Lajos " ,
eletkor = 25 ,
vegzettseg = new List < string > { " Le Cordon Bleu "
},
_fkod = " f3 " ,
_e_f = " e1 "
};
foszakacsCollection . InsertOne ( ujFoszakacs ) ;
Console . WriteLine ( " Sikeres b e s z u r s ! " ) ;




/////////////////////////////////////////////////
var filter = Builders < Etterem >. Filter . Eq ( e = > e . nev , "
Valhalla " ) ;
var update = Builders < Etterem >. Update . Set ( e = > e . csillag ,
3) ;
etteremCollection . UpdateOne ( filter , update ) ;*
Console . WriteLine ( " Sikeres m d o s t s ! " ) ;

////////////////////////////////////////////////////
/// 
var filter = Builders < Foszakacs >. Filter . Lt ( f = > f . eletkor
, 30) ;
foszakacsCollection . DeleteMany ( filter ) ;
Console . WriteLine ( " Sikeres t r l s ! " ) ;



var filter = Builders < Gyakornok >. Filter . Eq ( g = > g . nev , "
S z i l g y i I s t v n ");
var update = Builders < Gyakornok >. Update . Push ( g = > g .
muszak , " jszaka " ) ;
gyakornokCollection . UpdateOne ( filter , update ) ;
Console . WriteLine ( " Sikeres hozzadas ! " ) ;




////////////////////////3. feladat
var reszlegek = szakacsCollection . Find ( _ = > true ) . ToList
() ;
foreach ( var sz in reszlegek )
{
    Console . WriteLine ( $ " { sz . nev } - { sz . reszleg } " ) ;
}


var result = etteremCollection . Find ( e = > e . csillag >= 4) .
ToList () ;
foreach ( var e in result )
{
    Console . WriteLine ( $ " N v : { e . nev } - Csillag : { e .csillag } " ) ;
}



var filter = Builders < Etterem >. Filter . Eq ( e = > e . cim . varos , "N y r e g y h z a "); |
Builders < Etterem >. Filter . Eq ( e = > e . csillag , 5) ;
var result = etteremCollection . Find ( filter ) . ToList () ;

foreach ( var e in result )
{
Console . WriteLine ( $ " { e . nev } - { e . cim . varos } - { e .csillag } " ) ;
}


var result = vendegCollection . Find ( v = > v . eletkor >= 25
&& v . eletkor <= 40) . ToList () ;
foreach ( var v in result )
{
Console . WriteLine ( $ " { v . nev } - { v . eletkor } " ) ;
}



///////4.fealdat

var result = etteremCollection . Aggregate ()
. Group ( e = > e . cim . varos , g = > new
{
Varos = g . Key ,
Darab = g . Count () ,
AtlagCsillag = g . Average ( x = > x . csillag )
})
. ToList () ;



foreach ( var r in result ){
    Console . WriteLine ( $ " { r . Varos } - db : { r . Darab } -tlag : { r . AtlagCsillag } " ) ;
}


var result = szakacsCollection . Aggregate ()
. Group ( s = > s . _e_sz , g = > new
{
EtteremKod = g . Key ,
Szam = g . Count ()
})
. ToList () ;

foreach ( var r in result )
{
Console . WriteLine ( $ " { r . EtteremKod } - { r . Szam } f");
}

var result = foszakacsCollection . Aggregate ()
. SortByDescending ( s = > s . eletkor )
. Group ( s = > s . _fkod , g = > new
{
EtteremKod = g . Key ,
LegidosebbNev = g . First () . nev ,
Kor = g . First () . eletkor
})
. ToList () ;


foreach ( var r in result )
{
Console . WriteLine ( $ " { r . EtteremKod } - { r . LegidosebbNev} ({ r . Kor }) " ) ;
}


var result = etteremCollection . Aggregate ()
. Lookup ( " szakacsok " , " _ekod " , " _e_sz " , " szakacsok " )
. ToList () ;


foreach ( var r in result )
{
    Console . WriteLine ( $ " tterem : { r [ " nev " ]} " ) ;
    var szakacsok = r [ " szakacsok " ]. AsBsonArray ;

    foreach ( var s in szakacsok )
    {
        Console . WriteLine ( $ " S z a k c s : { s [ " nev " ]} " ) ;
    }

}