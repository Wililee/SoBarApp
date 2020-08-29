package sobar.app.service.model;

import java.util.List;

public class DistanceMatrixResponse {

    private class RowDTO {
        public List<ElementDTO> elements;
    }

    private class ElementDTO {
        public TextValueDTO distance;
        public TextValueDTO duration;
        public String status;
    }

    private class TextValueDTO {
        public String text;
        public int value;
    }

    private List<String> destinationAddresses;
    private List<String> originAddresses;
    private List<RowDTO> rows;

    private String status;

    public int getDistance() {
        return getDistance(0, 0);
    }

    public int getDistance(int origin, int destination) {
        return rows.get(origin).elements.get(destination).distance.value;
    }

    public int getDuration() {
        return getDuration(0, 0);
    }

    public int getDuration(int origin, int destination) {
        return rows.get(origin).elements.get(destination).duration.value;
    }

    public List<String> getDestinationAddresses() {
        return destinationAddresses;
    }

    public List<String> getOriginAddresses() {
        return originAddresses;
    }

    public String getStatus() {
        return status;
    }

}