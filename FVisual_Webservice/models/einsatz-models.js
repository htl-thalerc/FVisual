class Einsatzart {
    constructor(id, beschreibung) {
        this.id = id;
        this.beschreibung = beschreibung;
    }
}

class Einsatzcode {
    constructor(id, code) {
        this.id = id;
        this.code = code;
    }
}

class Einsatzort {
    constructor(id, adresse) {
        this.id = id;
        this.adresse = adresse;
    }
}

class Andere_Organisation {
    constructor(id, name) {
        this.id = id;
        this.name = name;
    }
}

class Einsatzkraft {
    constructor(stuetzpunkt) {
        this.stuetzpunkt = stuetzpunkt;
        this.mitglieder = mitglieder;
        this.einsatzfahzeuge = einsatzfahrzeuge;
    }
    setMitglieder() {

    }

    setEinsatzfahrzeuge() {

    }
}

class Einsatz {
    constructor(id, einsatzart, einsatzcode, einsatzort, titel, kurzbeschreibung, datum, zeit) {
        this.id = id;
        this.einsatzart = einsatzart;
        this.einsatzcode = einsatzcode;
        this.einsatzort = einsatzort;
        this.titel = titel;
        this.kurzbeschreibung = kurzbeschreibung;
        this.datum = datum;
        this.zeit = zeit;
    }

    setAndereOrganisationen() {

    }

    setFeuerwehren() {

    }
}

module.exports.Einsatzart = Einsatzart;
module.exports.Einsatzcode = Einsatzcode;
module.exports.Einsatzort = Einsatzort;
module.exports.Andere_Organisation = Andere_Organisation;
module.exports.Einsatzkraft = Einsatzkraft;
module.exports.Einsatzart = Einsatzart;
module.exports.Einsatzart = Einsatzart;
module.exports.Einsatz = Einsatz;