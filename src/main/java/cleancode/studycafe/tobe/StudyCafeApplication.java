package cleancode.studycafe.tobe;

import cleancode.studycafe.tobe.io.provider.LockerPassFileReader;
import cleancode.studycafe.tobe.io.provider.SeatPassFileReader;
import cleancode.studycafe.tobe.provider.LockerPassProvier;
import cleancode.studycafe.tobe.provider.SeatPassProvier;

public class StudyCafeApplication {

    public static void main(String[] args) {
        SeatPassProvier seatPassProvier = new SeatPassFileReader();
        LockerPassProvier lockerPassProvider = new LockerPassFileReader();

        StudyCafePassMachine studyCafePassMachine = new StudyCafePassMachine(seatPassProvier, lockerPassProvider);
        studyCafePassMachine.run();
    }

}
