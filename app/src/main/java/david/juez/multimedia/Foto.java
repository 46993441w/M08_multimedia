package david.juez.multimedia;

public class Foto {
    String ruta;
    String name;
    Double lat;
    Double lon;

    public Foto(){

    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String message) {
        this.ruta = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }
}
