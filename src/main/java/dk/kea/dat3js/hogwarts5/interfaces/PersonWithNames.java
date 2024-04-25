package dk.kea.dat3js.hogwarts5.interfaces;

public interface PersonWithNames {
    String getFirstName();
    String getMiddleName();
    String getLastName();
    void setFirstName(String firstName);
    void setMiddleName(String middleName);
    void setLastName(String lastName);

    default String getFullName(){
        return getFirstName() + " " + (getMiddleName() != null ? getMiddleName() + " " : "") + getLastName();
    }
    default void setFullName(String fullName){
        if (fullName == null || fullName.isEmpty()){
            return;
        } else {
            int firstSpace = fullName.indexOf(' ');
            int lastSpace = fullName.lastIndexOf(' ');

            setFirstName(fullName.substring(0, firstSpace));
            if(firstSpace != lastSpace){
                setMiddleName(fullName.substring(firstSpace + 1, lastSpace));
                setLastName(fullName.substring(lastSpace + 1));
            } else {
                setLastName(fullName.substring(firstSpace + 1));
            }
        }
    }
    
    default String capitalize(String name){
        if (name == null || name.isEmpty()){
            return name;
        }
        String[] parts = name.split(" ");
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].substring(0, 1).toUpperCase() + parts[i].substring(1).toLowerCase();
        }
        return String.join(" ", parts);
    }
}
