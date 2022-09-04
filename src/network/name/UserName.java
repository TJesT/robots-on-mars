package network.name;

import network.exception.InvalidUserNameException;

import java.io.Serializable;
import java.util.Objects;

public class UserName implements Name, Serializable {
    private final String name;


    public UserName(String name) throws InvalidUserNameException {
        if (!matchesSample(name)) {
            throw new InvalidUserNameException();
        }
        this.name = name;
    }
    @Override
    public boolean matchesSample(String name) {
        return !(name == null) && name.matches(".*\\w.*");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserName userName = (UserName) o;
        return Objects.equals(name, userName.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
