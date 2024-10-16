package cleancode.studycafe.tobe.model;

import java.util.List;

public class StudyCafeLockerPasses {

    private final List<StudyCafeLockerPass> lockerPasses;

    private StudyCafeLockerPasses(List<StudyCafeLockerPass> lockerPasses) {
        this.lockerPasses = lockerPasses;
    }

    public static StudyCafeLockerPasses of(List<StudyCafeLockerPass> lockerPasses) {
        return new StudyCafeLockerPasses(lockerPasses);
    }

    //이후 pass에 대한 lockerPass 리스트가 잘 나오는지 테스트 추가 필요!
    public StudyCafeLockerPass findLockerPassBy(StudyCafePass pass) {
        return lockerPasses.stream()
                .filter(pass::isSameDurationType)
                .findFirst()
                .orElse(null);
    }
}
