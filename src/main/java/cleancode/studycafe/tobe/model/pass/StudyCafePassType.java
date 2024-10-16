package cleancode.studycafe.tobe.model.pass;

import java.util.Set;

public enum StudyCafePassType {

    HOURLY("시간 단위 이용권"),
    WEEKLY("주 단위 이용권"),
    FIXED("1인 고정석");

    private static final Set<StudyCafePassType> LOCKER_PASS = Set.of(StudyCafePassType.FIXED);

    private final String description;

    StudyCafePassType(String description) {
        this.description = description;
    }

    public boolean isLockerType(){
        return LOCKER_PASS.contains(this);
    }

    public boolean isNotLockerType() {
        return !isLockerType();
    }
}
