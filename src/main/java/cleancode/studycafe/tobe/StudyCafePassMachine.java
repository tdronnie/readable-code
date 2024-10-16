package cleancode.studycafe.tobe;

import cleancode.studycafe.tobe.exception.AppException;
import cleancode.studycafe.tobe.io.StudyCafeIOHandler;
import cleancode.studycafe.tobe.model.order.StudyCafePassOrder;
import cleancode.studycafe.tobe.model.pass.*;
import cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPasses;
import cleancode.studycafe.tobe.provider.LockerPassProvier;
import cleancode.studycafe.tobe.provider.SeatPassProvier;

import java.util.List;
import java.util.Optional;

public class StudyCafePassMachine {

    private final StudyCafeIOHandler ioHandler = new StudyCafeIOHandler();
    private final SeatPassProvier seatPassProvier;
    private final LockerPassProvier lockerPassProvier;

    public StudyCafePassMachine(SeatPassProvier seatPassProvier, LockerPassProvier lockerPassProvier) {
        this.seatPassProvier = seatPassProvier;
        this.lockerPassProvier = lockerPassProvier;
    }

    public void run() {
        try {
            ioHandler.showWelcomeMessage();
            ioHandler.showAnnouncement();

            StudyCafeSeatPass selectedPass = selectPass();

            Optional<StudyCafeLockerPass> optionalLockerPass = selectLockerPass(selectedPass);

            StudyCafePassOrder passOrder = StudyCafePassOrder.of(selectedPass, optionalLockerPass.orElse(null));

            ioHandler.showPassOrderSummary(passOrder);

        } catch (AppException e) {
            ioHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            ioHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    //이용할 패스 정하는 메서드
    private StudyCafeSeatPass selectPass() {
        StudyCafePassType passType = ioHandler.askPassTypeSelecting();
        List<StudyCafeSeatPass> passCandidates = findPassCandidatesBy(passType);

        return ioHandler.askPassSelecting(passCandidates);
    }

    //패스 선택 후 가능한 이용권들만 리턴하는 메서드
    private List<StudyCafeSeatPass> findPassCandidatesBy(StudyCafePassType studyCafePassType) {
        StudyCafeSeatPasses allPasses = seatPassProvier.getSeatPasses();
        return allPasses.findPassBy(studyCafePassType);
    }

    //Fixed패스 경우만 사물함 이용권 정하는 메서드
    private Optional<StudyCafeLockerPass> selectLockerPass(StudyCafeSeatPass selectedPass) {
        //Fixed 패스가 아닌가? -> 사물함 옵션을 사용할 수 있는 타입이 아닌가? (필요한 관심사)
        if(selectedPass.cannotUseLocker()){
            return Optional.empty();
        }

        Optional<StudyCafeLockerPass> lockerPassCandidate = findLockerPassCandidateBy(selectedPass);

        if (lockerPassCandidate.isPresent()) {
            StudyCafeLockerPass lockerPass = lockerPassCandidate.get();

            boolean isLockerSelected = ioHandler.askLockerPassSelecting(lockerPass);
            if(isLockerSelected) {
                return Optional.of(lockerPass); //Optional로 감싸서 넘긴다.
            }
        }
        return Optional.empty();
    }

    //Fixed패스, 사물함 이용 선택 후 가능한 이용권만 리턴하는 메서드
    private Optional<StudyCafeLockerPass> findLockerPassCandidateBy(StudyCafeSeatPass pass) {
        StudyCafeLockerPasses allLockerPasses = lockerPassProvier.getLockerPasses();
        return allLockerPasses.findLockerPassBy(pass);
    }

}
