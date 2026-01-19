# První projekt – Pokojové rostliny (Java)

Projekt do předmětu (Projekt 1): evidence pokojových rostlin, jejich zálivky a práce se seznamem rostlin včetně načítání/ukládání do souboru.

---

## Zadání (stručně)

### Úkol 1: Model dat
Třída `Plant` ukládá informace o rostlině:
- název (name)
- poznámky (notes)
- datum zasazení (planted)
- datum poslední zálivky (watering)
- frekvence zálivky ve dnech (frequencyOfWatering)

Konstruktory:
1. všechny atributy
2. poznámky prázdné + planted a watering = dnešní datum
3. jen název (poznámky prázdné, planted/watering dnes, frekvence 7 dní)

Gettery a settery pro všechny atributy.

---

### Úkol 2: Metody
- `getWateringInfo()` – text: název, poslední zálivka a doporučená další zálivka
- `doWateringNow()` – nastaví poslední zálivku na dnešní datum

---

### Úkol 3: Ošetření nesprávného vstupu
Vlastní výjimka `PlantException` (extends `Exception`).

Validace:
- frekvence zálivky musí být kladné číslo (> 0)
- datum poslední zálivky nesmí být dřívější než datum zasazení

Validace je kontrolována i v konstruktoru.

---

### Úkol 4: Správa seznamu květin
Třída `PlantManager` pro správu seznamu rostlin:
- přidání nové rostliny
- získání rostliny dle indexu
- odebrání rostliny
- získání kopie seznamu
- získání rostlin k zalití (dle poslední zálivky + frekvence)

---

### Úkol 5: Řazení a práce s rostlinami
Řazení rostlin:
- podle názvu (výchozí)
- podle poslední zálivky

---

### Úkol 6: Načtení ze souboru a uložení do souboru
Načítání a ukládání seznamu rostlin pomocí tabulátorů (`\t`).

Podporované testovací soubory:
- `kvetiny.txt`
- `kvetiny-spatne-datum.txt`
- `kvetiny-spatne-frekvence.txt`

Při chybě se vyhazuje výjimka a aplikace pokračuje s prázdným seznamem.

---

### Úkol 7: Ověření funkčnosti (main)
V `Main` je testovací kód:
1. načtení `kvetiny.txt`
2. výpis informací o zálivce pro všechny rostliny
3. přidání nové rostliny
4. přidání 10× “Tulipán na prodej 1..10”
5. odebrání 3. položky (index 2)
6. uložení do nového souboru
7. opětovné načtení výstupu
8. řazení podle různých kritérií

---

## Struktura projektu

- `src/` – zdrojové kódy
- `data/` – textové soubory se seznamem rostlin (vstupní data)
- `README.md` – tento soubor

---

## Práce se soubory

Textové soubory jsou ve složce `data/` a používají tabulátory jako oddělovač.

Formát řádku:
name<TAB>notes<TAB>frequency<TAB>watering<TAB>planted


---

## Spuštění

Spusťte třídu:
- `Main`

Program načte data, vypíše zálivku, přidá/odebere rostliny, uloží nový soubor a ověří opětovné načtení.

---

## Poznámky
- Při chybách při načítání souboru se vypíše chybová hláška a aplikace pokračuje s prázdným seznamem (aplikace nepadá).
