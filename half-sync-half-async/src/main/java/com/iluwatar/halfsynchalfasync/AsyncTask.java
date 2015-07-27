package com.iluwatar.halfsynchalfasync;

import java.util.concurrent.Callable;

/**
 * Represents some computation that is performed asynchronously and its result. 
 * The computation is typically done is background threads and the result is posted 
 * back in form of callback. The callback does not implement {@code isComplete}, {@code cancel} 
 * as it is out of scope of this pattern.
 * 
 * @param <O> type of result
 */
public interface AsyncTask<O> extends Callable<O> {
	/**
	 * Is called in context of caller thread before call to {@link #call()}. Large
	 * tasks should not be performed in this method. Validations can be performed here
	 * so that the performance penalty of context switching is not incurred in case of
	 * invalid requests.
	 */
	void preExecute();
	
	/**
	 * A callback called after the result is successfully computed by {@link #call()}.
	 */
	void onResult(O result);
	
	/**
	 * A callback called if computing the task resulted in some exception. This method
	 * is called when either of {@link #call()} or {@link #preExecute()} throw any exception.
	 * 
	 * @param throwable error cause
	 */
	void onError(Throwable throwable);
	
	/**
	 * This is where the computation of task should reside. This method is called in context
	 * of background thread.
	 */
	@Override
	O call() throws Exception;
}
