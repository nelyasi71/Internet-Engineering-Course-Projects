import requests

headers = {
    "Content-Type": "application/json",
    "Accept": "application/json"
}

add_admin = {
    "role": "admin",
    "username": "admin",
    "password": "1234",
    "email": "my.mail@mail.com",
    "address": {
        "country": "Iran",
        "city": "Karaj"
    }
}

add_matin = {
    "role": "customer",
    "username": "matin",
    "password": "1234",
    "email": "my.mail2@mail.com",
    "address": {
        "country": "Iran",
        "city": "Karaj"
    }
}

login_matin = {
    "username": "matin",
    "password": "1234",
}
login_admin = {
    "username": "admin",
    "password": "1234",
}

add_author = {
    "name": "author", 
    "penName": "abc",
    "born": "1982-04-12"
}

add_book = {
    "title": "dishonored", 
    "author": "author", 
    "publisher": "name", 
    "year": 2012, 
    "price": 250, 
    "synopsis": "lorem",
    "content": "lorem ipsum", 
    "genres": ["horror", "thriller"]
}

add_review = {
    "title": "tale of two cities", 
    "rate": 4, 
    "comment": "This isthe perfect book!"
}

cart = {"title": "dishonored"}

borrow = {
    "title": "dishonored",
    "days": 2
}

addcredit = {
    "credit": 8200
}

# Create a session to persist cookies across requests
session = requests.Session()
session.headers.update(headers)

try:
    
    # response = session.post('http://localhost:9090/api/user', json=add_matin, timeout=1)
    # print(response.text)

    # response = session.post('http://localhost:9090/api/user', json=add_admin, timeout=1)
    # print(response.text)

    # response = session.post('http://localhost:9090/api/auth/login', json=login_admin, timeout=1)
    # print(response.text)
    
    # response = session.post('http://localhost:9090/api/author', json=add_author, timeout=1)
    # print(response.text)
    
    # response = session.post('http://localhost:9090/api/book', json=add_book, timeout=1)
    # print(response.text)
    
    # response = session.post('http://localhost:9090/api/auth/logout', json={}, timeout=1)
    # print(response.text)
    

    ################# No Login

    # response = session.get('http://localhost:9090/api/users/matin', timeout=1)
    # print(response.text)

    # response = session.get('http://localhost:9090/api/authors/author', timeout=1)
    # print(response.text)
    
    # response = session.get('http://localhost:9090/api/books/dishonored', timeout=1)
    # print(response.text)

    # response = session.get('http://localhost:9090/api/books/dishonored/reviews', timeout=1)
    # print(response.text)

    # response = session.get('http://localhost:9090/api/books?title=dis&genre=horror', timeout=1)
    # print(response.text)
    
    #################

    response = session.post('http://localhost:9090/api/auth/login', json=login_matin, timeout=1)
    print(response.text)
    

    # response = session.post('http://localhost:9090/api/credit', json=addcredit, timeout=1)
    # print(response.text)

    # response = session.post('http://localhost:9090/api/cart/add', json=cart, timeout=1)
    # print(response.text)

    # response = session.post('http://localhost:9090/api/cart/purchase', json=cart, timeout=1)
    # print(response.text)

    # response = session.post('http://localhost:9090/api/cart/borrow', json=borrow, timeout=1)
    # print(response.text)

    # response = session.get('http://localhost:9090/api/cart/list', timeout=1)
    # print(response.text)

    # response = session.delete('http://localhost:9090/api/cart/remove', json=cart, timeout=1)
    # print(response.text)

    # response = session.delete('http://localhost:9090/api/cart/remove', json=cart, timeout=1)
    # print(response.text)

    # response = session.get('http://localhost:9090/api/purchase-history', timeout=1)
    # print(response.text)

    # response = session.get('http://localhost:9090/api/purchased-books', timeout=1)
    # print(response.text)

    response = session.post('http://localhost:9090/api/books/tale%20of%20two%20cities/review', json=add_review, timeout=1)
    print(response.text)
    
    # response = session.get('http://localhost:9090/api/books/dishonored/content', timeout=1)
    # print(response.text)

except requests.exceptions.RequestException as e:
    print(f"Request failed: {e}")
