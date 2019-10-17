class Stuetzpunkt {
    constructor(id, name, ort, plz, strasse, hausnr) {
        this.id = id;
        this.name = name;
        this.ort = ort;
        this.plz = plz;
        this.strasse = strasse;
        this.hausnr = hausnr;
        this.fahrzeuge = null;
        this.mitglieder = null;
    }

    setFahrzeuge(fahrzeuge) {
        this.fahrzeuge = fahrzeuge;
    }

    setMitglieder(mitglieder) {
        this.mitglieder = mitglieder;
    }
}

module.exports = Stuetzpunkt;