package model;

import org.junit.Test;

import static model.Point.*;
import static org.assertj.core.api.Assertions.*;

public class PointTest {

    @Test
    public void should_get_next_point_by_current_point() {
       assertThat(ZERO.getNextPoint()).isEqualTo(FIFTEEN);
       assertThat(FIFTEEN.getNextPoint()).isEqualTo(THIRTY);
       assertThat(THIRTY.getNextPoint()).isEqualTo(FOURTY);
       assertThat(FOURTY.getNextPoint()).isEqualTo(WIN);
       assertThat(WIN.getNextPoint()).isEqualTo(ZERO);
    }
}