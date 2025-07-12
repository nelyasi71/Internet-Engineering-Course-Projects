filename = "./miobook/secrets/db_password.txt"
content = "Estanboli2003"

with open(filename, "w", encoding="utf-16") as file:
    file.write(content)

print(f"File '{filename}' written successfully encoding.")
