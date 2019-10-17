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
        this.andere_Organisationen = null;
        this.feuerwehren = null;
    }

    setAndereOrganisationen(andere_Organisationen) {
        this.andere_Organisationen = andere_Organisationen;
    }

    setFeuerwehren(feuerwehren) {
        this.feuerwehren = feuerwehren;
    }
}

module.exports = Einsatz;