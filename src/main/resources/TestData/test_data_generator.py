"""
这是一个生成测试数据的生成脚本
使用python的faker库
仅生成user student_info resume_experience job job_favorite company这六张表的数据
符合逻辑外键约束
默认头像和logo为空
修改下面数据库连接信息，然后直接跑一遍就行
"""

import random
import mysql.connector
from faker import Faker
from datetime import datetime, timedelta

# 配置数据库连接
db_config = {
    'host': 'localhost',
    'user': 'root',
    'password': '1234',
    'database': 'shixi',
    'raise_on_warnings': True
}

# 创建Faker实例
fake = Faker('zh_CN')


def create_connection():
    """创建数据库连接"""
    try:
        connection = mysql.connector.connect(**db_config)
        return connection
    except mysql.connector.Error as err:
        print(f"数据库连接错误: {err}")
        return None


def generate_users(num_users):
    """生成用户数据"""
    users = []
    for _ in range(num_users):
        phone = fake.phone_number()
        openid = fake.uuid4() if random.random() > 0.3 else None  # 30%的用户没有微信openid
        password = fake.password(length=12)
        nick_name = fake.user_name()
        icon = ""  # 默认头像为空

        users.append((phone, openid, password, nick_name, icon))
    return users


def generate_student_infos(user_ids):
    """生成学生简历数据"""
    student_infos = []
    genders = ['男', '女', None]
    education_levels = ['本科', '大专', '硕士', '博士', '其他']
    expected_positions = ['互联网-产品实习生', '软件开发实习生', '数据分析师实习生', '市场营销实习生', '人力资源实习生']

    for user_id in user_ids:
        name = fake.name()
        gender = random.choice(genders)
        phone = fake.phone_number()
        birth_date = fake.date_of_birth(minimum_age=18, maximum_age=28).strftime('%Y/%m')
        school_name = fake.unique.company() + "大学"
        major = fake.job().split(' ')[0] + "专业"
        icon = ""  # 默认头像为空
        graduation_date = (datetime.now() + timedelta(days=random.randint(0, 365))).date()
        wechat = fake.user_name() if random.random() > 0.2 else None
        education_level = random.choice(education_levels)
        advantages = fake.paragraph(nb_sentences=3)
        expected_position = random.choice(expected_positions)

        student_infos.append((
            user_id, name, gender, phone, birth_date, school_name, major,
            icon, graduation_date, wechat, education_level, advantages,
            expected_position
        ))
    return student_infos


def generate_resume_experiences(student_info_ids):
    """生成简历经历数据"""
    resume_experiences = []
    experience_types = ['工作', '实习', '项目', '作品集']

    for student_info_id in student_info_ids:
        # 每个学生有1-3个经历
        num_experiences = random.randint(1, 3)
        for _ in range(num_experiences):
            exp_type = random.choice(experience_types)
            description = fake.paragraph(nb_sentences=5)
            link = f"https://example.com/{exp_type}/{fake.slug()}" if random.random() > 0.5 else None

            resume_experiences.append((student_info_id, exp_type, description, link))
    return resume_experiences


def generate_companies(num_companies):
    """生成企业数据"""
    companies = []
    industries = ['互联网', '金融', '教育', '医疗健康', '电子商务', '人工智能', '新能源']
    locations = ['北京市海淀区', '上海市浦东新区', '广州市天河区', '深圳市南山区', '杭州市西湖区']
    scales = ['100-499人', '500-999人', '1000-9999人', '10000人以上', '少于50人']
    types = ['外企', '民营', '国企', '上市公司', '初创公司']

    for _ in range(num_companies):
        name = fake.unique.company()
        logo = ""  # 默认企业logo为空
        industry = random.choice(industries)
        description = fake.paragraph(nb_sentences=2)
        location = random.choice(locations)
        contact_person = fake.name()
        contact_phone = fake.phone_number()
        contact_email = fake.email()
        website = f"https://{name.lower().replace(' ', '-')}.com"
        scale = random.choice(scales)
        company_type = random.choice(types)
        status = random.choice([0, 1, 2])  # 0-禁用，1-正常，2-待审核

        companies.append((
            name, logo, industry, description, location, contact_person,
            contact_phone, contact_email, website, scale, company_type, status
        ))
    return companies


def generate_jobs(company_ids, user_ids):
    """生成职位数据"""
    jobs = []
    titles = ['产品经理实习生', '前端开发实习生', '后端开发实习生', '测试开发实习生', '数据挖掘实习生',
              'UI设计师实习生']
    frequencies = ['3天/周', '4天/周', '5天/周']
    total_times = ['3个月', '6个月', '6个月以上', '长期']
    onboard_times = ['一周内', '两周内', '一个月内', '两个月内']
    enterprise_types = ['外企', '校友企业', '国企', '民营', '上市公司']
    financing_progresses = ['天使轮', 'A轮', 'B轮', 'C轮', 'D轮及以上', '已上市', '不需要融资']
    work_locations = ['北京市海淀区', '上海市浦东新区', '广州市天河区', '深圳市南山区', '杭州市西湖区']
    categories = ['技术', '产品', '设计', '运营', '市场', '销售', '人力资源', '财务']
    job_types = ['实习', '全职', '兼职']
    tags = ['线下-可转正', '远程-灵活办公', '薪资高-福利好', '技术氛围好', '大厂背景']

    for company_id in company_ids:
        # 每个公司发布2-5个职位
        num_jobs = random.randint(2, 5)
        for _ in range(num_jobs):
            publisher_id = random.choice(user_ids)
            title = random.choice(titles)
            salary_min = random.randint(8, 30) * 100
            salary_max = salary_min + random.randint(5, 20) * 100
            frequency = random.choice(frequencies)
            total_time = random.choice(total_times)
            onboard_time = random.choice(onboard_times)
            enterprise_type = random.choice(enterprise_types)
            publisher = fake.name()
            enterprise_name = fake.company()
            financing_progress = random.choice(financing_progresses)
            enterprise_scale = random.choice(['100-499人', '500-999人', '1000-9999人', '10000人以上', '少于50人'])
            work_location = random.choice(work_locations)
            detailed_information = fake.paragraph(nb_sentences=10)
            category = random.choice(categories)
            job_type = random.choice(job_types)
            tag = random.choice(tags)
            status = random.choices([0, 1], weights=[0.8, 0.2])[0]  # 80%的职位可申请，20%已截止

            jobs.append((
                publisher_id, company_id, title, salary_min, salary_max, frequency,
                total_time, onboard_time, enterprise_type, publisher, enterprise_name,
                financing_progress, enterprise_scale, work_location, detailed_information,
                category, job_type, tag, status
            ))
    return jobs


def generate_job_favorites(user_ids, job_ids):
    """生成岗位收藏数据"""
    job_favorites = []

    # 每个用户收藏3-8个岗位
    for user_id in user_ids:
        # 随机选择一些岗位
        favorite_jobs = random.sample(job_ids, random.randint(3, min(8, len(job_ids))))
        for job_id in favorite_jobs:
            job_favorites.append((user_id, job_id))

    return job_favorites


def insert_data():
    """插入测试数据到数据库"""
    connection = create_connection()
    if not connection:
        return

    cursor = connection.cursor()

    try:
        # 1. 插入用户数据
        print("正在生成用户数据...")
        users = generate_users(50)
        user_insert_query = """
        INSERT INTO user (phone, openid, password, nick_name, icon)
        VALUES (%s, %s, %s, %s, %s)
        """
        cursor.executemany(user_insert_query, users)
        connection.commit()

        # 获取插入的用户ID
        cursor.execute("SELECT id FROM user")
        user_ids = [row[0] for row in cursor.fetchall()]

        # 2. 插入学生简历数据
        print("正在生成学生简历数据...")
        student_infos = generate_student_infos(user_ids[:30])  # 假设前30个用户是学生
        student_info_insert_query = """
        INSERT INTO student_info (
            user_id, name, gender, phone, birth_date, school_name, major, 
            icon, graduation_date, wechat, education_level, advantages, 
            expected_position
        ) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
        """
        cursor.executemany(student_info_insert_query, student_infos)
        connection.commit()

        # 获取插入的学生简历ID
        cursor.execute("SELECT id FROM student_info")
        student_info_ids = [row[0] for row in cursor.fetchall()]

        # 3. 插入简历经历数据
        print("正在生成简历经历数据...")
        resume_experiences = generate_resume_experiences(student_info_ids)
        resume_experience_insert_query = """
        INSERT INTO resume_experience (student_info_id, type, description, link)
        VALUES (%s, %s, %s, %s)
        """
        cursor.executemany(resume_experience_insert_query, resume_experiences)
        connection.commit()

        # 4. 插入企业数据
        print("正在生成企业数据...")
        companies = generate_companies(20)
        company_insert_query = """
        INSERT INTO company (
            name, logo, industry, description, location, contact_person, 
            contact_phone, contact_email, website, scale, type, status
        ) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
        """
        cursor.executemany(company_insert_query, companies)
        connection.commit()

        # 获取插入的企业ID
        cursor.execute("SELECT id FROM company")
        company_ids = [row[0] for row in cursor.fetchall()]

        # 5. 插入职位数据
        print("正在生成职位数据...")
        jobs = generate_jobs(company_ids, user_ids[30:])  # 假设后20个用户是企业HR
        job_insert_query = """
        INSERT INTO job (
            publisher_id, company_id, title, salary_min, salary_max, frequency, 
            total_time, onboard_time, enterprise_type, publisher, enterprise_name, 
            financing_progress, enterprise_scale, work_location, detailed_information, 
            category, type, tag, status
        ) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
        """
        cursor.executemany(job_insert_query, jobs)
        connection.commit()

        # 获取插入的职位ID
        cursor.execute("SELECT id FROM job")
        job_ids = [row[0] for row in cursor.fetchall()]

        # 6. 插入岗位收藏数据
        print("正在生成岗位收藏数据...")
        job_favorites = generate_job_favorites(user_ids[:30], job_ids)  # 只有学生用户会收藏岗位
        job_favorite_insert_query = """
        INSERT INTO job_favorite (user_id, job_id)
        VALUES (%s, %s)
        """
        cursor.executemany(job_favorite_insert_query, job_favorites)
        connection.commit()

        print("测试数据插入完成！")

    except mysql.connector.Error as err:
        print(f"数据插入错误: {err}")
        connection.rollback()
    finally:
        cursor.close()
        connection.close()


if __name__ == "__main__":
    insert_data()