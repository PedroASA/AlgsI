class Count(object):
	"""docstring for Test"""
	def __init__(self, n, t):
		super(Count, self).__init__()
		self.tosses = 0
		self.eggs = 0
		self.n = n
		self.t = t
	
	def breaks_egg(self, x):
		self.tosses += 1
		if x >= self.t:
			self.eggs += 1
			return True
		return False
		


	def seq(self):
		i = 1	
		while i < self.n: 
			if self.breaks_egg(i): 
				break
			i += 1

	def bin(self):
		left = 1
		right = self.n

		while left <= right:
			mid = left + (right - left)/2
			t = self.breaks_egg(mid)

			if t and not self.breaks_egg(mid-1):
				break
			
			if t:
				right = mid

			else:
				left = mid+1		

def test():

	d = {
			0: "seq",
			1: "bin",
			2: "v2",
			3: "v3",
			4: "v4"
	}

	while True:
		n = int(input("N:"))
		t = int(input("T:"))
		v = int(input("Version (0 to 4):"))	

		c = Count(n, t)
		
		eval(f"c.{d[v]}()")

		print(f"{c.eggs} broken eggs in {c.tosses} tosses")

		if input("stop?y/n") == "y":
			break

test()