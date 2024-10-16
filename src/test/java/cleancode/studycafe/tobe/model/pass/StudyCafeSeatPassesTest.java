package cleancode.studycafe.tobe.model.pass;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class StudyCafeSeatPassesTest {

    @Test
    @DisplayName("passType에 대한 StudyCafePass 리스트만을 출력한다.")
    void findByPassType(){
        //given
        StudyCafeSeatPass pass1 = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 2, 4000, 0.0);
        StudyCafeSeatPass pass2 = StudyCafeSeatPass.of(StudyCafePassType.HOURLY,4,6500,0.0);
        StudyCafeSeatPass pass3 = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 6,9000,0.0);
        StudyCafeSeatPass pass4 = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 8,11000,0.0);
        StudyCafeSeatPass pass5 = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 10,12000,0.0);
        StudyCafeSeatPass pass6 = StudyCafeSeatPass.of(StudyCafePassType.WEEKLY,1,60000,0.0);

        List<StudyCafeSeatPass> list = Arrays.asList(pass1, pass2, pass3, pass4, pass5, pass6);
        StudyCafeSeatPasses seatPasses = StudyCafeSeatPasses.of(list);

        //when
        List<StudyCafeSeatPass> findSeatPassList = seatPasses.findPassBy(StudyCafePassType.WEEKLY);

        //then
        assertEquals(1, findSeatPassList.size());
        assertTrue(findSeatPassList.contains(pass6));

    }

}