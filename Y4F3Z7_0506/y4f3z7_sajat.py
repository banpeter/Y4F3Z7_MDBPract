import pymongo as mongo

# Kapcsolódás a MongoDB szerverhez
client = mongo.MongoClient("mongodb://localhost:27017/")

db = client.local

# Collection létrehozása
ugyfel_coll = db["ugyfel"]
aru_coll = db["aru"]
rendeles_coll = db["rendeles"]
futar_coll = db["futar"]
kiszallitas_coll = db["kiszallitas"]
jarmu_coll = db["jarmu"]
uzlethelyseg_coll = db["uzlethelyseg"]
alkalmazott_coll = db["alkalmazott"]

# Kollekciók törlése (friss start)
ugyfel_coll.delete_many({})
aru_coll.delete_many({})
rendeles_coll.delete_many({})
futar_coll.delete_many({})
kiszallitas_coll.delete_many({})
jarmu_coll.delete_many({})
uzlethelyseg_coll.delete_many({})
alkalmazott_coll.delete_many({})

# ─────────────────────────────────────────────
# Adatok feltöltése
# ─────────────────────────────────────────────

ugyfelek = [
    {
        "_id": "c1",
        "nev": "Kiss Péter",
        "email": "kiss.peter@gmail.hu",
        "telefonszam": "06301234567",
        "kartyaszam": "1234567812345678",
        "ceg": False,
        "cim": {
            "orszag": "Magyarország",
            "varos": "Budapest",
            "iranyitoszam": "1111",
            "utca": "Fő utca",
            "hazszam": 12
        }
    },
    {
        "_id": "c2",
        "nev": "Tóth Mária",
        "email": "toth.maria@gmail.hu",
        "telefonszam": "06209876543",
        "kartyaszam": "8765432187654321",
        "ceg": True,
        "cim": {
            "orszag": "Magyarország",
            "varos": "Debrecen",
            "iranyitoszam": "4025",
            "utca": "Piac utca",
            "hazszam": 3
        }
    }
]

aruk = [
    {
        "_id": "a10",
        "nev": "Laptop",
        "gyarto": "Lenovo",
        "kategoria": "Elektronika",
        "raktarkeszlet": 5,
        "garancia_honap": 24,
        "ertekelesek": [5, 5, 4]
    },
    {
        "_id": "a11",
        "nev": "Egér",
        "gyarto": "Logitech",
        "kategoria": "Elektronika",
        "raktarkeszlet": 20,
        "garancia_honap": 12,
        "ertekelesek": [4, 3]
    }
]

rendeles_adatok = [
    {
        "_id": "r100",
        "ugyfel_ref": "c1",
        "rendeles_datum": "2025-01-10",
        "fizetve": True,
        "osszeg": 250000,
        "tetelek": [
            {"aru_ref": "a10", "darab": 1}
        ]
    },
    {
        "_id": "r101",
        "ugyfel_ref": "c2",
        "rendeles_datum": "2025-02-05",
        "fizetve": False,
        "osszeg": 15000,
        "tetelek": [
            {"aru_ref": "a11", "darab": 3}
        ]
    }
]

futarok = [
    {
        "_id": "f20",
        "nev": "Nagy László",
        "munkaido": {"kezd": "08:00", "vegez": "16:00"},
        "fizetes": 400000,
        "benzinkartya": True
    },
    {
        "_id": "f21",
        "nev": "Varga Béla",
        "munkaido": {"kezd": "12:00", "vegez": "20:00"},
        "fizetes": 350000,
        "benzinkartya": False
    }
]

kiszallitasok = [
    {
        "futar_ref": "f20",
        "rendeles_ref": "r100",
        "felvett": "2025-01-11",
        "kiszallitva": "2025-01-12"
    }
]

jarmuvek = [
    {
        "_id": "j5",
        "rendszam": "ABC-123",
        "marka": "Ford",
        "megtett_km": 120000,
        "szervizben": False,
        "garancia_vege": "2026-01-01",
        "teherbiras_tonna": 6
    }
]

uzlethelyisegek = [
    {
        "_id": "u3",
        "cim": {
            "orszag": "Magyarország",
            "varos": "Győr",
            "iranyitoszam": "9022",
            "utca": "Baross utca",
            "hazszam": 5
        },
        "atvetel_lehetseges": True
    }
]

alkalmazottak = [
    {
        "_id": "a50",
        "uzlet_ref": "u3",
        "nev": "Szabó Anna",
        "fizetes": 350000,
        "kezdes_eve": 2020,
        "vezeto": False
    },
    {
        "_id": "a51",
        "uzlet_ref": "u3",
        "nev": "Horváth Zoltán",
        "fizetes": 420000,
        "kezdes_eve": 2018,
        "vezeto": True
    }
]

ugyfel_coll.insert_many(ugyfelek)
print("Ügyfelek feltöltve.")
aru_coll.insert_many(aruk)
print("Áruk feltöltve.")
rendeles_coll.insert_many(rendeles_adatok)
print("Rendelések feltöltve.")
futar_coll.insert_many(futarok)
print("Futárok feltöltve.")
kiszallitas_coll.insert_many(kiszallitasok)
print("Kiszállítások feltöltve.")
jarmu_coll.insert_many(jarmuvek)
print("Járművek feltöltve.")
uzlethelyseg_coll.insert_many(uzlethelyisegek)
print("Üzlethelyiségek feltöltve.")
alkalmazott_coll.insert_many(alkalmazottak)
print("Alkalmazottak feltöltve.")

# ─────────────────────────────────────────────
# 2.a) Összes lekérdezés
# ─────────────────────────────────────────────

print("\n--- 2.a) Összes ügyfél ---")
for u in ugyfel_coll.find():
    print(u)

print("\n--- 2.a) Összes áru ---")
for a in aru_coll.find():
    print(a)

print("\n--- 2.a) Összes rendelés ---")
for r in rendeles_coll.find():
    print(r)

print("\n--- 2.a) Összes futár ---")
for f in futar_coll.find():
    print(f)

print("\n--- 2.a) Összes alkalmazott ---")
for a in alkalmazott_coll.find():
    print(a)

# ─────────────────────────────────────────────
# 2.b) Konkrét elem lekérdezése (_id: r100)
# ─────────────────────────────────────────────

print("\n--- 2.b) Rendelés lekérdezése (_id: r100) ---")
r100 = rendeles_coll.find_one({"_id": "r100"})
print(r100)

# ─────────────────────────────────────────────
# 2.c) Ki nem fizetett rendelések
# ─────────────────────────────────────────────

print("\n--- 2.c) Ki nem fizetett rendelések ---")
for r in rendeles_coll.find({"fizetve": False}):
    print(r)

# ─────────────────────────────────────────────
# 2.d) Futárok átlagos fizetése
# ─────────────────────────────────────────────

print("\n--- 2.d) Futárok átlagos fizetése ---")
pipeline_avg = [
    {
        "$group": {
            "_id": None,
            "atlagFizetes": {"$avg": "$fizetes"}
        }
    }
]
atlag_eredmeny = list(futar_coll.aggregate(pipeline_avg))
atlag = atlag_eredmeny[0]["atlagFizetes"]
print(f"A futárok átlagos fizetése: {atlag:,.0f} Ft")

# ─────────────────────────────────────────────
# 2.e) Benzinkártyás futárok és kiszállításaik (lookup)
# ─────────────────────────────────────────────

print("\n--- 2.e) Benzinkártyás futárok és kiszállításaik ---")
pipeline = [
    {
        "$match": {"benzinkartya": True}
    },
    {
        "$lookup": {
            "from": "kiszallitas",
            "localField": "_id",
            "foreignField": "futar_ref",
            "as": "kiszallitas_adatok"
        }
    }
]
for doc in futar_coll.aggregate(pipeline):
    k_lista = doc["kiszallitas_adatok"]
    if k_lista:
        for k in k_lista:
            print(f"Futár: {doc['nev']} -> Rendelés: {k['rendeles_ref']}, kiszállítva: {k['kiszallitva']}")
    else:
        print(f"Futár: {doc['nev']} -> Nincs kiszállítás")

# ─────────────────────────────────────────────
# 3.a) Módosítás: r101 rendelés megjelölése fizetve
# ─────────────────────────────────────────────

print("\n--- 3.a) r101 rendelés módosítása: fizetve = True ---")
rendeles_coll.update_one(
    {"_id": "r101"},
    {"$set": {"fizetve": True}}
)

print("\n--- 3.a) Összes rendelés módosítás után ---")
for r in rendeles_coll.find():
    print(r)

# ─────────────────────────────────────────────
# 4.a) Törlés: konkrét alkalmazott (_id: a50)
# ─────────────────────────────────────────────

print("\n--- 4.a) Konkrét alkalmazott törlése (_id: a50) ---")
alkalmazott_coll.delete_one({"_id": "a50"})

print("\n--- Ellenőrzés: megmaradt alkalmazottak ---")
for a in alkalmazott_coll.find():
    print(a)

# ─────────────────────────────────────────────
# 4.b) Törlés: szervizben lévő járművek törlése
# ─────────────────────────────────────────────

print("\n--- 4.b) Szervizben lévő járművek törlése ---")
torles_eredmeny = jarmu_coll.delete_many({"szervizben": True})
print(f"Törölt járművek száma: {torles_eredmeny.deleted_count}")

print("\n--- Ellenőrzés: megmaradt járművek ---")
for j in jarmu_coll.find():
    print(j)
