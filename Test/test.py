import requests

BASE = "http://localhost:8080/api"
make_header = lambda token: {"Authorization": f"Bearer {token}"}


add_admin_data = {
    "role": "admin",
    "username": "admin",
    "password": "admin",
    "email": "admin@adminestan.com",
    "address": {"country": "Iran", "city": "Karaj"}
}

add_charlie_data = {
    "role": "customer",
    "username": "charlie",
    "password": "1234",
    "email": "my.mail2@mail.com",
    "address": {"country": "Iran", "city": "Karaj"}
}

login_admin_data = {"username": "admin", "password": "admin"}
login_charlie_data = {"username": "charlie", "password": "1234"}

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
addcredit = {"credit": 10000}
add_review = {"title": "peanuts", "rate": 5, "comment": "This is the perfect book!"}

class APITest:
    token = ""
    charlie_token = ""

    def add_admin(self):
        r = requests.post(f"{BASE}/user", json=add_admin_data, timeout=0.05)
        print("add_admin:", r.json())

    def add_charlie(self):
        r = requests.post(f"{BASE}/user", json=add_charlie_data, timeout=0.05)
        print("add_charlie:", r.json())

    def login_admin(self):
        r = requests.post(f"{BASE}/auth/login", json=login_admin_data, timeout=0.05)
        print("login_admin:", r.json())
        self.__class__.token = r.json().get("data", {}).get("token", "")

    def login_charlie(self):
        r = requests.post(f"{BASE}/auth/login", json=login_charlie_data, timeout=0.05)
        print("login_charlie:", r.json())
        self.__class__.charlie_token = r.json().get("data", {}).get("token", "")

    def add_author(self):
        for a in authors:
            r = requests.post(f"{BASE}/author", json=a, headers=make_header(self.token), timeout=0.05)
            print("add_author:", r.json())

    def add_book(self):
        for b in books:
            r = requests.post(f"{BASE}/book", json=b, headers=make_header(self.token), timeout=0.05)
            print("add_book:", r.json())

    def add_credit(self):
        r = requests.post(f"{BASE}/credit", json=addcredit, headers=make_header(self.charlie_token), timeout=0.05)
        print("add_credit:", r.json())

    def add_cart(self):
        r1 = requests.post(f"{BASE}/cart/add", json=cart1, headers=make_header(self.charlie_token), timeout=0.05)
        print("add_cart1:", r1.json())
        r2 = requests.post(f"{BASE}/cart/add", json=cart2, headers=make_header(self.charlie_token), timeout=0.05)
        print("add_cart2:", r2.json())

    def borrow_book(self):
        r = requests.post(f"{BASE}/cart/borrow", json=borrow, headers=make_header(self.charlie_token), timeout=0.05)
        print("borrow:", r.json())

    def list_cart(self):
        r = requests.get(f"{BASE}/cart/list", headers=make_header(self.charlie_token), timeout=0.05)
        print("cart_list:", r.json())

    def purchase(self):
        r = requests.post(f"{BASE}/cart/purchase", json={}, headers=make_header(self.charlie_token), timeout=0.05)
        print("purchase:", r.json())

    def history(self):
        r = requests.get(f"{BASE}/purchase-history", headers=make_header(self.charlie_token), timeout=0.05)
        print("history:", r.json())

    def add_review(self):
        r = requests.post(f"{BASE}/books/peanuts/review", json=add_review, headers=make_header(self.charlie_token), timeout=0.05)
        print("add_review:", r.json())

    def logout(self):
        r = requests.post(f"{BASE}/auth/logout", json={}, headers=make_header(self.token), timeout=0.05)
        print("logout:", r.json())

if __name__ == "__main__":
    t = APITest()

    t.add_admin()
    t.add_charlie()
    t.login_admin()
    t.add_author()
    t.add_book()
    t.logout()
    t.login_charlie()
    t.add_credit()
    t.add_cart()
    t.borrow_book()
    t.list_cart()
    t.purchase()
    t.history()
    t.add_review()
    t.logout()