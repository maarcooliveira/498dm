
package com.squareup.moshi;

import org.junit.Test;

import java.lang.reflect.AnnotatedElement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;

public final class UtilTest {
    @Test public void isAnnotationPresent() throws Exception {

        AnnotatedElement a = Util.NO_ANNOTATIONS;
        assertFalse(a.isAnnotationPresent(null));
    }


    // Test m8
    @Test public void getAnnotations() throws Exception {
        AnnotatedElement a = Util.NO_ANNOTATIONS;
        assertThat(a.getAnnotations()).isEqualTo(Util.EMPTY_ANNOTATIONS_ARRAY);
    }


    // Test m9
    @Test public void getDeclaredAnnotations() throws Exception {
        AnnotatedElement a = Util.NO_ANNOTATIONS;
        assertThat(a.getDeclaredAnnotations()).isEqualTo(Util.EMPTY_ANNOTATIONS_ARRAY);
    }
}







