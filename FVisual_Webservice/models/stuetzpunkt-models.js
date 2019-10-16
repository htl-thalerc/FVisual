class Dienstgrad {
    constructor(id, kuerzel, bezeichnung) {
        this.id = id;
        this.kuerzel = kuerzel;
        this.bezeichnung = bezeichnung;
    }
}

class Mitglied {
    constructor(id, dienstgrad, vorname, nachname, stuetzpunkt, isAdmin) {
        this.id = id;
        this.dienstgrad = dienstgrad;
        this.vorname = vorname;
        this.nachname = nachname;
        this.stuetzpunkt = stuetzpunkt;
        this.isAdmin = isAdmin;
    }
}

class Einsatzfahrzeuge {
    constructor(id, rufname, stuetzpunkt) {
        this.id = id;
        this.rufname = rufname;
        this.stuetzpunkt = stuetzpunkt;
    }
}


class Stuetzpunkt {
    constructor(id, name, ort, plz, strasse, hausnr) {
        this.id = id;
        this.name = name;
        this.ort = ort;
        this.plz = plz;
        this.strasse = strasse;
        this.hausnr = hausnr;
    }

    setFahrzeuge() {

    }

    setMitglieder() {

    }
    setFahrzeuge() {

    }

}

module.exports.Dienstgrad = Dienstgrad;
module.exports.Mitglied = Mitglied;
module.exports.Einsatzfahrzeuge = Einsatzfahrzeuge;
module.exports.Stuetzpunkt = Stuetzpunkt;