from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.support.ui import Select
from selenium.webdriver.common.by import By
from selenium.webdriver.support import expected_conditions as ec
import json

from selenium.webdriver.support.wait import WebDriverWait

s = Service(executable_path='../venv/chromedriver.exe')
driver = webdriver.Chrome(service=s)
driver.get('https://lms.ou.edu.vn/')

driver.implicitly_wait(5)

driver.find_element(By.CLASS_NAME, 'main-btn').click()
driver.find_element(By.CLASS_NAME, 'login100-form-btn').click()

user_type = Select(driver.find_element(By.ID, 'form-usertype'))
user_type.select_by_index(0)

with open('C:/Users/admin/Desktop/accounts.json', 'r') as f:
  data = json.load(f)
  user = driver.find_element(By.ID, 'form-username')
  user.send_keys(data['username'])
  password = driver.find_element(By.ID, 'form-password')
  password.send_keys(data['password'])

driver.find_element(By.CLASS_NAME, 'm-loginbox-submit-btn').click()
driver.implicitly_wait(15)

#results = driver.find_elements(By.CSS_SELECTOR, '#paged-content-container-1 .coursename')
results = WebDriverWait(driver, 30).until(
  ec.presence_of_all_elements_located((By.CSS_SELECTOR, '#paged-content-container-1 .coursename'))
)

for re in results:
    print(re.text)

driver.execute_script('window.scrollTo(0, 700)')
driver.get_screenshot_as_file("test.png")

driver.quit()