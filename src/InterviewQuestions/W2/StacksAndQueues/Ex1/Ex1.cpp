/*
The idea:
    Mantain two stacks: one for queuing (q) and the other for dequeuing (dq).
    On queue: 
        push to q.
    On deque: 
        if dq is empty than transfer all elements from q to dq, elements will enter dq in the reverse order they entered q. 
    This way, at the top of dq will be the first element pushed to q, so popping it will be the same, order wise, as dequeing from q.
    In case dq has any elements, just pop from it.  

Despite 'dequeue' taking linear time worst case, it has constant amortized case.
That derives from the fact that each element enqueued can be moved three more times at most:
	- twice in stack transfer (poped from q and pushed to dq)
	- once in dequeue (popped from dq)
 
By analizing elements individually, one can conclude that each of them is moved a constant number of times (at most 4).
Therefore both methods public methods have constant amortized time.
*/
 
#include <iostream>
#include <stack>
 
using namespace std;
 
template<class T>
class MyQueue {
  private:
 
	stack<T> *q = new stack<T>;
	stack<T> *dq = new stack<T>;
 
	int cnt = 0;
 
	void stack_transfer() {
		while(!q->empty()) {
			dq->push(q->top());
			q->pop();
		}
	}
 
  public:
	T dequeue() {
		if(dq->empty()) {
			stack_transfer();	
		}
		auto tmp = dq->top();
		dq->pop();
		return tmp;
	}
 
	void enqueue(T val) {
		q->push(val);
	}
 
};
 
int main() {
	MyQueue<int> q;
	for(int i = 0; i < 10; i++) {q.enqueue(i);}
	for(int i = 0; i < 10; i++) cout << q.dequeue() << endl;
	return 0;
}