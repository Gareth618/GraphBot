# GraphBot

Dream Team:

- Iulian Oleniuc
- Elisabeta Dima

Aplicația este compusă din trei module principale:

1. **aplicație grafică**: Aici putem desena grafuri care apoi să fie stocate în baza de date sau să fie exportate în format SVG, PNG sau TIKZ.
2. **bot discord**: Aici putem selecta un graf din baza de date pe care mai apoi să rulăm diverși algoritmi.
3. **server spring**: El se ocupă de rularea algoritmilor pe grafuri și de transformarea acestora în poze pentru discord, oferind API-uri pentru celelalte două module.

## 🎨 Aplicația grafică

În stânga avem un panel pătratic în care putem desena un graf astfel:

- Dând click într-un punct liber, acolo se creează un nod nou.
- Dând click într-un punct ocupat de un nod, acesta va fi (de)selectat pentru crearea ulterioară a unei muchii.
- Când avem două noduri selectate, se va crea o muchie de la primul la al doilea și se vor deselecta.
- Dacă muchia exista deja, atunci se va completa de la tastatură textul aferent muchiei.
- Când apăsăm pe *backspace* se șterge ultimul nod creat.

În dreapta-sus avem trei butoane pentru cele trei tipuri de export-uri. Fișierele rezultate vor fi salvate în folder-ul `Downloads`. În dreapta-jos avem un buton pentru selectarea orientării grafului, unul pentru salvarea grafului în baza de date și unul pentru crearea unui graf nou. Butonul `save` face actualizări pe același graf până la apăsarea lui `new`. Eventual, la apăsarea lui `new` va apărea un prompt care să ne anunțe că avem modificări nesalvate.

## 🤖 Bot-ul pentru Discord

Comenzile încep cu `$ spațiu` și sunt *case insensitive*:

- `select graph`: Selectează un graf din baza de date pentru a lucra cu el.
    - ⬅️ 🆗 ➡️
    - `no graphs to choose from`
    - `user ${nickname} chose graph ${id} + poză`
- `run ${alg}`: Rulează algoritmul dat pe ultimul graf selectat și afișează pașii acestuia sub forma unui șir de poze.
    - `dfs s=1`, `bfs s=1`, `kosaraju`, `prim`, `biconnectivity`, `flow s=1 t=${n}`:
    - `step ${i} of ${n} + poză`
    - ⏪ ⬅️ ➡️ ⏩
    - `source and sink are not different`
    - `the graph is not (un)directed`
    - `no graph selected`

## 🗄️ Server-ul Spring

Comunică cu baza de date PostgreSQL și oferă următoarele API-uri pentru celelalte două module:

- **POST** `/graphs` Dai graful ca JSON pentru a fi inserat în baza de date. Folosit de aplicația grafică.
- **GET** `/graphs` Primești vectorul de grafuri ca JSON. Folosit de bot.
- **GET** `/algo/:alg/:id` Primești un vector de grafuri ilustrând etapele algoritmului `alg` rulat pe graful `id`. Folosit de bot.

Bot-ul va converti singur grafurile în poze:

```
{
  id: int
  directed: bool
  nodes: [
    {
      x: int
      y: int
      text: string
      color: string
    }
  ]
  edges: [
    node1: int
    node2: int
    text: string
    color: string
  ]
}
```
