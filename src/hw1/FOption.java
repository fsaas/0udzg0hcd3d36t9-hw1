package hw1;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional; 
import java.util.function.Consumer; 
import java.util.function.Function; 
import java.util.function.Predicate; 
import java.util.function.Supplier; 

public final class FOption<T> {
	private final T value;
	private final boolean defined;
	private FOption(T value, boolean defined) {
		this.value = value;
		this.defined = defined;
	}

	//  SFM : yield <value, true> 
	public static <T> FOption<T> of(T value) {
        return new FOption<T>(value ,true);
	}

	// SFM : yield <null, false> 
	public static <T> FOption<T> empty() { 
		return new FOption<T>(null, false);
	}
	
	// Java optional¿¡¼­ cast 
	public static <T> FOption<T> from(Optional<T> opt) { 
		return new FOption<T>(opt.get(), true);
	}

	// if defined is true 
	public boolean isPresent() {
		return defined;
	}
	
	// if defined is false 
	public boolean isAbsent() {
		return defined;
	}

	public T get() { 
        if (value == null) {
            throw new NoSuchElementException("No value present");
        }
        return value;
	}
	
	public T getOrNull() { 
		return value != null ? value : null;
	}
	
	public T getOrElse(T elseValue) { 
		return value != null ? value : elseValue;
	}
	
	public T getOrElse(Supplier<T> elseSupplier) { 
		return value != null ? value : elseSupplier.get();
	}

	public <X extends Throwable> T getOrElseThrow(Supplier<X> thrower) throws X{
		if (value != null) {
			return value;
		} else {
			throw thrower.get();	
		}
	}

	public FOption<T> ifPresent(Consumer<T> effect) { 
		if(this.isPresent()) {
        	effect.accept(value);
		}
		return this;
	}
	
	public FOption<T> ifAbsent(Runnable orElse) { 
		if(this.isAbsent()) {
			orElse.run();
		}
		return this;
	}

	public FOption<T> filter(Predicate<T> pred) { 
		Objects.requireNonNull(pred);
		if (!isPresent()) {
			return this;
		} else {
			return pred.test(value) ? this : empty();
		}
	}

	public boolean test(Predicate<T> pred) { 
		if (defined && pred.test(value)) 
			return true; 
		return false; 
	}
	
	public <S> FOption<S> map(Function<T,S> mapper) {
		if (defined) 
			return FOption.of(mapper.apply(value)); 
		else
			return FOption.empty(); 
	}
	
	@Override 
	public String toString() { 
		return defined? String.format("FOption(%s)", value) : "FOption.empty"; 
	}
	
	@Override 
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Optional)) {
			return false;
		}
		Optional<?> other = (Optional<?>) o;
		return Objects.equals(value, other.of(value));
	}
	
	@Override 
	public int hashCode() { 
		return Objects.hashCode(this.value);
	}
}
