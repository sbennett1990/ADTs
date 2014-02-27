/**
 * A DoubleLinkedSeq is a sequence of doubles.  The sequence
 * can have a special current element, which is specified and
 * accessed through four methods that are not available in the
 * IntArrayBag class (start, getCurrent, advance, and 
 * isCurrent).
 * 
 * Limitations:
 * 
 *     Beyond Integer.MAX_VALUE element, the size
 *         method does not work.
 * 
 * @author  Scott Bennett
 * @version 2014.02.26
 */
public class DoubleLinkedSeq implements Cloneable {
    // Count of how many nodes in the sequence
    private int manyNodes;
    private DoubleNode head;
    private DoubleNode tail;
    private DoubleNode cursor;
    private DoubleNode precursor;
    

    /**
     * Initializes an empty DoubleLinkedSeq.
     *
     * @postcondition This sequence is empty.
     */
    public DoubleLinkedSeq() {
        head = null;
        tail = null;
        cursor = null;
        precursor = null;
        manyNodes = 0;
    }

    /**
     * Adds a new element to this sequence before the cursor.
     *
     * @param element the new element that is being added to this sequence.
     *
     * @postcondition a new copy of the element has been added to this
     *                sequence.  If there was a current element, then this
     *                method places the new element before the current element.
     *                If there was no current element, then this method places
     *                the new element at the front of this sequence.  The newly
     *                added element becomes the new current element of this
     *                sequence.
     */
    public void addBefore(double element) {
        //Empty sequence
        if (head == null && tail == null) {
            head = new DoubleNode(element);
            tail = head;
            cursor = head;
        }
        //Non-empty sequence with no current element
        else if (cursor == null) {
            head = new DoubleNode(element, head);
            cursor = head;
        }
        //Non-empty sequence with current element equal to head
        else if (precursor == null){
            head = new DoubleNode(element, head);
            cursor = head;
        }
        //Non-empty sequence with current element that is not head
        else if (cursor != null && precursor != null) {
            DoubleNode newElement = new DoubleNode(element, cursor);
            precursor.setLink(newElement);
            cursor = newElement;
        }
        
        manyNodes++;
    }

    /**
     * Adds a new element to this sequence after the cursor.
     *
     * @param element the new element that is being added to this sequence.
     *
     * @postcondition a new copy of the element has been added to this
     *                sequence.  If there was a current element, then this
     *                method places the new element after the current element.
     *                If there was no current element, then this method places
     *                the new element at the end of this sequence.  The newly
     *                added element becomes the new current element of this
     *                sequence.
     */
    public void addAfter(double element) {
        //Empty sequence
	if (manyNodes == 0) {
		head = new DoubleNode(element);
		tail = head;
		cursor = head;
	}
	//Non-empty sequence with one element
	else if (manyNodes == 1) {
		DoubleNode newElement = new DoubleNode(element);
		head.setLink(newElement);
		tail = newElement;
		cursor = tail;
		precursor = head;
	}
	//Non-empty sequence with cursor equal to head
	else if (cursor == head) {
		DoubleNode newElement = new DoubleNode(element, cursor.getLink());
		head.setLink(newElement);
		cursor = cursor.getLink();
		precursor = head;
	}
	//Non-empty sequence with cursor not equal to head
	else if (cursor != head && precursor != null) {
		DoubleNode newElement = new DoubleNode(element, cursor.getLink());
		cursor.setLink(newElement);

                if (cursor == tail) {
                    tail = tail.getLink();
                }

		cursor = cursor.getLink();
		precursor = precursor.getLink();
	}
	//Non-empty sequence with no current element
	else if (!isCurrent()) {
		DoubleNode newElement = new DoubleNode(element);
		tail.setLink(newElement);
		precursor = tail;
		tail = tail.getLink();
		cursor = tail;
	}

        manyNodes++;
    }

    /**
     * Places the contents of another sequence at the end of this sequence.
     *
     * @precondition other must not be null.
     *
     * @param other a sequence show contents will be placed at the end of this
     *              sequence.
     *
     * @postcondition the elements from other have been placed at
     *                the end of this sequence.  The current element of this
     *                sequence remains where it was, and other is
     *                unchanged.
     *
     * @throws NullPointerException if other is null.
     */
    public void addAll(DoubleLinkedSeq other) {
        if (other == null) {
            throw new NullPointerException("Second sequence cannot be null.");
        } else {
            DoubleLinkedSeq otherClone = other.clone();
            tail.setLink(otherClone.head);
            tail = otherClone.tail;
            manyNodes += otherClone.size();
        }
    }

    /**
     * Move forward so that the current element is now the next element in the
     * sequence.
     *
     * @precondition isCurrent() returns true.
     *
     * @postcondition If the current element was already the end element of
     *                this sequence (with nothing after it), then there is no
     *                longer any current element.  Otherwise, the new element
     *                is the element immediately after the original current
     *                element.
     *
     * @throws IllegalStateException if there is not current element.
     */
    public void advance() {
        if (isCurrent()) {
            if (cursor == tail){
                cursor = null;
                precursor = null;
            } else if (cursor != tail && cursor != head) {
                cursor = cursor.getLink();
                precursor = precursor.getLink();
            } else if (cursor == head) {
                cursor = cursor.getLink();
                precursor = head;
            }
        } else {
            throw new IllegalStateException("There is no current element.");
        }
    }

    /**
     * Creates a copy of this sequence.
     *
     * @return a copy of this sequence.  Subsequent changes to the copy will not
     *         affect the original, nor vice versa.
     *
     * @throws RuntimeException if this class does not implement Cloneable.
     */
    @Override
    public DoubleLinkedSeq clone() {
        DoubleLinkedSeq answer;
        
        try {
            answer = (DoubleLinkedSeq) super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException
                    ("This class does not implement Cloneable.");
        }
        
        //Sequence has no current element
        if (cursor == null) {
            DoubleNode[] newList = DoubleNode.listCopyWithTail(head);
        
            answer.head = newList[0];
            answer.tail = newList[1];
        }
        //Sequence with a current element equal to head
        else if (cursor == head) {
            DoubleNode[] newList = DoubleNode.listCopyWithTail(head);
            answer.head = newList[0];
            answer.tail = newList[1];
            
            answer.precursor = null;
            answer.cursor = answer.head;
        }
        //Sequence with a current element not head
        else if (cursor != null && precursor != null) {
            //listPart
            DoubleNode[] firstPart = DoubleNode.listPart(head, precursor);
            answer.head = firstPart[0];
            answer.precursor = firstPart[1];
            
            DoubleNode[] secondPart = DoubleNode.listPart(cursor, tail);
            answer.cursor = secondPart[0];
            answer.tail = secondPart[1];

            answer.precursor.setLink(answer.cursor);
        }
        
        return answer;
    }

    /**
     * Creates a new sequence that contains all the elements from s1 followed
     * by all of the elements from s2.
     *
     * @precondition neither s1 nor s2 are null.
     *
     * @param s1 the first of two sequences.
     * @param s2 the second of two sequences.
     *
     * @return a new sequence that has the elements of s1 followed by the
     *         elements of s2 (with no current element).
     *
     * @throws NullPointerException if s1 or s2 are null.
     */
    public static DoubleLinkedSeq concatenation(DoubleLinkedSeq s1,
                                                DoubleLinkedSeq s2) {
        if (s1.size() == 0 || s2.size() == 0) {
            throw new NullPointerException("The sequences cannot be null.");
        } else {
            DoubleLinkedSeq newSeq = s1.clone();
            newSeq.addAll(s2);

            while (newSeq.isCurrent()) {
                newSeq.advance();
                /**
                  Horrible programming, just a quick fix to satisfy the
                  condition that the returned sequence has no current
                  element since the clone method copies the current.
                **/
            }

            return newSeq;
        }
    }

    /**
     * Returns a copy of the current element in this sequence.
     *
     * @precondition isCurrent() returns true.
     *
     * @return the current element of this sequence.
     *
     * @throws IllegalStateException if there is no current element.
     */
    public double getCurrent() {
        if (isCurrent()) {
            return cursor.getData();
        } else {
            throw new IllegalStateException("There is no current element.");
        }
    }
    
    /**
     * Determines whether this sequence has specified a current element.
     *
     * @return true if there is a current element, or
     *         false otherwise.
     */
    public boolean isCurrent() {
        // If the cursor is not null then there must be a current element
        if (cursor != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes the current element from this sequence.
     *
     * @precondition  isCurrent() returns true.
     *
     * @postcondition The current element has been removed from this sequence,
     *                and the following element (if there is one) is now the new
     *                current element.  If there was no following element, then
     *                there is now no current element.
     *
     * @throws IllegalStateException if there is no current element.
     */
    public void removeCurrent() {
        if (!isCurrent()) {
            throw new IllegalStateException("There is no current element.");
        } else {
            //Only one element
            if (head == tail) {
                head = null;
                tail = null;
                cursor = null;
                manyNodes--;
            }
            //Cursor points to head
            else if (cursor == head && precursor == null) {
                head = head.getLink();
                cursor = head;
                manyNodes--;
            }
            //If cursor != tail then cursor = next element after
            else if (cursor != tail && precursor != null) {
                cursor = cursor.getLink();
                precursor.setLink(cursor);
                manyNodes--;
            }
            //If cursor == tail then cursor & precursor = null after
            else if (cursor == tail && precursor != null) {
                cursor = null;
                precursor.setLink(null);
                precursor = null;
                manyNodes--;
            }
        }
    }


    /**
     * Determines the number of elements in this sequence.
     *
     * @return the number of elements in this sequence.
     */
    public int size() {
       return manyNodes;
    }

    /**
     * Sets the current element to the front of this sequence.
     *
     * @postcondition If this sequence is not empty, the front element of this
     *                sequence is now the current element; otherwise, there is
     *                no current element.
     */
    public void start() {
        if (head != null) {
            cursor = head;
        } else {
            cursor = null;
        }
        
        precursor = null;
    }


    /**
     * Returns a String representation of this sequence.  If the sequence is
     * empty, the method should return "<>".  If the sequence has one item,
     * say 1.1, and that item is not the current item, the method
     * should return "<1.1>".  If the sequence has more than one item, they 
     * should be separated by commas, for example: "<1.1, 2.2, 3.3>".  
     * If there exists a current item, then that item should be surrounded 
     * by square braces.  For example, if the second item is the current 
     * item, the method should return: "<1.1, [2.2], 3.3>".
     *
     * @return a String representation of this sequence.
     */
    @Override
    public String toString() {
        String answer = "";
        
        //First, all cases where there is no current element
        if (!isCurrent()) {
            //Empty sequence
            if (head == null) {
                answer += "<>";
            }
            //Sequence with one element
            else if (manyNodes == 1) {
                answer += "<" + head.getData() + ">";
            }
            //Sequence with more than one element
            else if (manyNodes > 1) {
                answer += "<";
                
                for (DoubleNode temp = head; temp != null; temp = temp.getLink()) {
                    answer += "" + temp.getData();
                    
                    if (temp.getLink() != null) {
                        answer += ", ";
                    }
                }
                
                answer += ">";
            }
        } else { //Now all cases where there is a current element
            //An empty sequence has no current, so it's not tested
            //Sequence with one element
            if (manyNodes == 1) {
                answer += "<[" + head.getData() + "]>";
            }
            //Sequence with more than one element
            else if (manyNodes > 1) {
                answer += "<";
                
                for (DoubleNode temp = head; temp != null; temp = temp.getLink()) {
                    
                    if (temp == cursor) {
                        answer += "[" + temp.getData() + "]";
                    } else {
                        answer += "" + temp.getData();
                    }

                    if (temp.getLink() != null) {
                        answer += ", ";
                    }
                }
                
                answer += ">";
            }
        }
        
        return answer;
    }

    /**
     * Determines if this object is equal to the other object.
     *
     * @return true if this object is equal to the other object, false
     *         otherwise.  Two sequences are equal if they have the
     *         same number of elements, and each corresponding element 
     *         is equal
     */
    @Override
    public boolean equals(Object other) {
        boolean b = true;
        DoubleLinkedSeq otherSeq = (DoubleLinkedSeq) other;
        
        if (manyNodes == otherSeq.manyNodes) {
            for (DoubleNode temp1 = head, temp2 = otherSeq.head;
                        temp1 != null;
                        temp1 = temp1.getLink(), temp2 = temp2.getLink()) {
                if (temp1.getData() != temp2.getData()) {
                    b = false;
                    break;
                }
                if (temp1 == cursor && temp2 != otherSeq.cursor) {
                    b = false;
                    break;
                }
                if (temp2 == otherSeq.cursor && temp1 != cursor) {
                    b = false;
                    break;
                }
            }
        } else {
            b = false;
        }
        
        return b;
    }
    
}

