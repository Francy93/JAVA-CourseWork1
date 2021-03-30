
public class Park extends Venue {
    
    private Integer numChangingFacilities;

    Park(String name, int changingF){
        this.name = name;
        numChangingFacilities = changingF;
    }
    
    
    
    public Integer getNumChangingFacilities() {
        return this.numChangingFacilities;
    }


}