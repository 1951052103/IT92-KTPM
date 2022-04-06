from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.by import By

driver = webdriver.Chrome(service=Service(executable_path='../venv/chromedriver.exe'))
driver.get('https://vnexpress.net/')

articles = driver.find_elements(By.CSS_SELECTOR, 'article.item-news')
for article in articles:
    try:
        print('====')
        print(article.find_element(By.TAG_NAME, 'h3').text)
        print(article.find_element(By.CSS_SELECTOR, '.thumb-art img').get_attribute('src'))
    except:
        pass

driver.quit()