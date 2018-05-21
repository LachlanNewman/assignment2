package views.enums;

public enum Relationship {
    FRIEND("friends"),
    COLLEAGUE("colleague"),
    CLASSMATE("classmate"),
    SPOUSE("couple"),
    PARENT("parent"),
    CHILD("child");

    private String relationshipType;

    Relationship(String relationshipType) {
        this.relationshipType = relationshipType;
    }

    public String getRelationshipType(){
        return relationshipType;
    }

    public static Relationship fromString(String relationshipType){
        for (Relationship relationship : Relationship.values()){
            if (relationship.getRelationshipType().equals(relationshipType)){
                return relationship;
            }
        }
        return null;
    }
}
