/*
All methods take constant time worst case.
The idea:
	Keep two stacks, one for pushing and popping (st) and the other for max queries (max).
	* On push:
		Push to st. If value pushed is greater or equal to max's top value, than push it to max. 

	* On pop:
		Pop from st. If popped value is equal to max's top, than pop max. 

Why does it work:
	Pushing: 
		Since we are only pushing to max values equal or greater than its top, we can be sure max's top is greater or equal to every other value on the stack.
	
	Popping: 
		The only way max's top can change is when its value is popped from stack. For its value to be popped, the stack must pop all elements pushed after it.
	Therefore, the stack return to the state before current max's top was pushed. State in which max former top was the greatest. 
*/

package main

import "fmt"



// Stack API
type Stack []int

func (st *Stack) push(val int) {
	*st = append(*st, val)
}

func (st *Stack) pop() {
	*st = (*st)[:len(*st)-1]
}

func (st *Stack) top() int {
	return (*st)[len(*st)-1]
}

func (st *Stack) isEmpty() bool {
	return len(*st) == 0
}



// StackMax API

type StackMax struct {
	st, max Stack
}

func (s *StackMax) Push(val int) {
	s.st.push(val)

	// if max is empty or val is greater or equal to max top value, push val to max
	if s.max.isEmpty() || val >= s.max.top() {
		s.max.push(val)
	}
}

func (s *StackMax) Pop() (val int, ok bool) {
	if !s.st.isEmpty() {
		ok = true
		val = s.st.top()
		s.st.pop()

		// if popped value is the top of max, pop max as well
		if val == s.max.top() {
			s.max.pop()
		}
	}
	return

}

func (s *StackMax) Max() (val int, ok bool) {
	if !s.max.isEmpty() {
		ok = true
		val = s.max.top()
	}
	return
}

func main() {
	s := new(StackMax)
	s.Push(1)
	s.Push(5)
	s.Push(2)
	s.Push(6)
	s.Push(4)
	s.Push(7)

	for val, ok := s.Pop(); ok; val, ok = s.Pop() {
		fmt.Println(val)
		tmp, _ := s.Max()
		fmt.Println("max:", tmp)
	}
}
