import requests

BASE = "http://localhost:9090/api"
make_header = lambda token: {"Authorization": f"Bearer {token}"}


add_admin_data = {
    "role": "admin",
    "username": "admin",
    "password": "admin",
    "email": "admin@adminestan.com",
    "address": {"country": "Iran", "city": "Karaj"}
}

add_googoolijan_data = {
    "role": "customer",
    "username": "googoolijan",
    "password": "1234",
    "email": "my.mail2@mail.com",
    "address": {"country": "Iran", "city": "Karaj"}
}

login_admin_data = {"username": "admin", "password": "admin"}
login_googoolijan_data = {"username": "googoolijan", "password": "1234"}

authors = [
    {"name": "Charles Monroe Schulz", "penName": "Sparky", "born": "1922-10-26", "died": "2000-01-12"},
    {"name": "Sami Antero Jarvi", "penName": "Sam Lake", "born": "1970-06-28"}
]

books = [
    {
        "title": "peanuts", "author": "Charles Monroe Schulz",
        "publisher": "Schulz pub.", "year": 1950, "price": 250,
        "synopsis": "lorem", "content": "lorem ipsum", "genres": ["cute", "drama"]
    },
    {
        "title": "Max Payne", "author": "Sami Antero Jarvi",
        "publisher": "Remedy", "year": 2001, "price": 250,
        "synopsis": "lorem", "content": "lorem ipsum", "genres": ["action", "thriller", "drama"]
    },
    {
        "title": "Control", "author": "Sami Antero Jarvi",
        "publisher": "Remedy", "year": 2019, "price": 10000,
        "synopsis": "lorem", "content": "lorem ipsum", "genres": ["action", "thriller", "horror"]
    }
]

cart1 = {"title": "Max Payne"}
cart2 = {"title": "peanuts"}
borrow = {"title": "Control", "days": 5}
addcredit = {"credit": 1000}
add_review = {"title": "peanuts", "rate": 5, "comment": "This is the perfect book!"}

class APITest:
    token = ""
    googoolijan_token = ""

    def add_admin(self):
        r = requests.post(f"{BASE}/user", json=add_admin_data, timeout=1)
        print("add_admin:", r.json())

    def add_googoolijan(self):
        r = requests.post(f"{BASE}/user", json=add_googoolijan_data, timeout=1)
        print("add_googoolijan:", r.json())

    def login_admin(self):
        r = requests.post(f"{BASE}/auth/login", json=login_admin_data, timeout=1)
        print("login_admin:", r.json())
        self.__class__.token = r.json().get("data", {}).get("token", "")

    def login_googoolijan(self):
        r = requests.post(f"{BASE}/auth/login", json=login_googoolijan_data, timeout=1)
        print("login_googoolijan:", r.json())
        self.__class__.googoolijan_token = r.json().get("data", {}).get("token", "")

    def add_author(self):
        for a in authors:
            r = requests.post(f"{BASE}/author", json=a, headers=make_header(self.token), timeout=1)
            print("add_author:", r.json())

    def add_book(self):
        for b in books:
            r = requests.post(f"{BASE}/book", json=b, headers=make_header(self.token), timeout=1)
            print("add_book:", r.json())

    def add_credit(self):
        r = requests.post(f"{BASE}/credit", json=addcredit, headers=make_header(self.googoolijan_token), timeout=1)
        print("add_credit:", r.json())

    def add_cart(self):
        r1 = requests.post(f"{BASE}/cart/add", json=cart1, headers=make_header(self.googoolijan_token), timeout=1)
        print("add_cart1:", r1.json())
        r2 = requests.post(f"{BASE}/cart/add", json=cart2, headers=make_header(self.googoolijan_token), timeout=1)
        print("add_cart2:", r2.json())

    def borrow_book(self):
        r = requests.post(f"{BASE}/cart/borrow", json=borrow, headers=make_header(self.googoolijan_token), timeout=1)
        print("borrow:", r.json())

    def list_cart(self):
        r = requests.get(f"{BASE}/cart/list", headers=make_header(self.googoolijan_token), timeout=1)
        print("cart_list:", r.json())

    def purchase(self):
        r = requests.post(f"{BASE}/cart/purchase", headers=make_header(self.googoolijan_token), timeout=1)
        print("purchase:", r.json())

    def history(self):
        r = requests.get(f"{BASE}/purchase-history", headers=make_header(self.googoolijan_token), timeout=1)
        print("history:", r.json())

    def add_review(self):
        r = requests.post(f"{BASE}/books/peanuts/review", json=add_review, headers=make_header(self.googoolijan_token), timeout=1)
        print("add_review:", r.json())


if __name__ == "__main__":
    t = APITest()

    # Call any you want manually
    # t.add_admin()
    # t.add_googoolijan()
    t.login_admin()
    # t.login_googoolijan()
    # t.add_author()
    t.add_book()
    # t.add_credit()
    # t.add_cart()
    # t.borrow_book()
    # t.list_cart()
    # t.purchase()
    # t.history()
    # t.add_review()
