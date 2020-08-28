package fbox.models;

import java.util.List;

public class WriteValue {

    public WriteValue(List<String> name, List<String> groupName) {
        this.names = name;
        this.groupnames = groupName;
    }
    public WriteValue(List<Long> ids) {
        this.ids = ids;
    }

    public List<String> names;
    public List<String> groupnames;
    public List<Long> ids;
}
