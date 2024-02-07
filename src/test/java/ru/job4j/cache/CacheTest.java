package ru.job4j.cache;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CacheTest {
    @Test
    public void whenAddFind() throws OptimisticException {
        Base base = new Base(1, "Base", 1);
        Cache cache = new Cache();
        cache.add(base);
        Optional<Base> find = cache.findById(base.id());
        assertThat(find.isPresent()).isTrue();
        assertThat(find.get().name())
                .isEqualTo("Base");
    }

    @Test
    public void whenAddUpdateFind() throws OptimisticException {
        Base base = new Base(1, "Base", 1);
        Cache cache = new Cache();
        cache.add(base);
        cache.update(new Base(1, "Base updated", 1));
        Optional<Base> find = cache.findById(base.id());
        assertThat(find.isPresent()).isTrue();
        assertThat(find.get().name())
                .isEqualTo("Base updated");
    }

    @Test
    public void whenAddDeleteFind() throws OptimisticException {
        Base base = new Base(1,  "Base", 1);
        Cache cache = new Cache();
        cache.add(base);
        cache.delete(1);
        Optional<Base> find = cache.findById(base.id());
        assertThat(find.isEmpty()).isTrue();
    }

    @Test
    public void whenMultiUpdateThrowException() throws OptimisticException {
        Base base = new Base(1, "Base", 1);
        Cache cache = new Cache();
        cache.add(base);
        cache.update(base);
        assertThatThrownBy(() -> cache.update(base))
                .isInstanceOf(OptimisticException.class);
    }

    @Test
    public void whenAddUpdateTrue() throws OptimisticException {
        Base base = new Base(1, "Base", 1);
        Cache cache = new Cache();
        cache.add(base);
        boolean rsl = cache.update(new Base(1, "Base updated", 1));
        assertThat(rsl).isTrue();
    }

    @Test
    public void whenAddUpdateVersion() throws OptimisticException {
        Base base = new Base(1, "Base", 1);
        Cache cache = new Cache();
        cache.add(base);
        cache.update(new Base(1, "Base", 1));
        Optional<Base> find = cache.findById(base.id());
        assertThat(find.isPresent()).isTrue();
        assertThat(find.get().version())
                .isEqualTo(2);
    }

    @Test
    public void whenAddUpdateDeleteFind() throws OptimisticException {
        Base base = new Base(1,  "Base", 1);
        Cache cache = new Cache();
        cache.add(base);
        cache.update(new Base(1, "Base updated", 1));
        Optional<Base> find = cache.findById(base.id());
        assertThat(find.isPresent()).isTrue();
        assertThat(find.get().name())
                .isEqualTo("Base updated");
        assertThat(find.get().version())
                .isEqualTo(2);
        cache.delete(1);
        find = cache.findById(base.id());
        assertThat(find.isEmpty()).isTrue();
    }
}
