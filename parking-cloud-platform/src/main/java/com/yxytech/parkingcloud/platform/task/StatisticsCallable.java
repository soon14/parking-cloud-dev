package com.yxytech.parkingcloud.platform.task;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class StatisticsCallable<E, F> implements Callable<Map<Long, F>> {
    private List<E> list;
    private Method method;
    private Date start;
    private Date end;

    public StatisticsCallable() {
    }

    public StatisticsCallable(List<E> list, Method method, Date start, Date end) {
        this.list = list;
        this.method = method;
        this.start = start;
        this.end = end;
    }

    @Override
    public Map<Long, F> call() throws Exception {
        return (Map<Long, F>) method.invoke(null, list, start, end);
    }
}
