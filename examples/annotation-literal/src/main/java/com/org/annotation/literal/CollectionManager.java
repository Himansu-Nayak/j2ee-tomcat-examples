package com.org.annotation.literal;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.LinkedList;

public class CollectionManager {
    private Collection collection;

    public CollectionManager(Collection collection) {
        this.collection = collection;
    }

    /**
     * Finds all objects matching all criteria represented by annotations
     * 
     * @param annotationList
     *            one or multiple search qualifiers
     * @return a collection of objects that match all the search qualifiers
     */
    public Collection findBy(Annotation... annotationList) {
        Collection result = new LinkedList();
        for (Object annotatedInstance : collection) {
            boolean matched = false;
            Annotation[] annotationListFromInstance = annotatedInstance.getClass().getAnnotations();
            for (Annotation annotation : annotationList) {
                if (contains(annotationListFromInstance, annotation)) {
                    matched = true;
                } else {
                    matched = false;
                    break;
                }
            }
            if (matched)
                result.add(annotatedInstance);
        }
        return result;
    }

    /**
     * Checks if an array of Annotations contains an individual Annotation
     * 
     * @param annotationListFromInstance
     *            an array of annotations
     * @param annotation
     *            an individual annotation
     * @return true if ann is equal to at least one of the element in annotations array; false otherwise
     */
    private boolean contains(Annotation[] annotationListFromInstance, Annotation annotation) {
        for (Annotation a : annotationListFromInstance) {
            if (a.equals(annotation)) {
                return true;
            }
        }
        return false;
    }
}
