import { createRouter, createWebHistory } from 'vue-router'
import Home from "@/views/Home";
import Login from "@/views/Login";
import Admin from "@/views/Admin";
import Teacher from "@/views/Teacher";
import Student from "@/views/Student";
import CheckCourse from "@/views/CheckCourse";
import AddCourse from "@/views/AddCourse";
import EditCourse from "@/views/EditCourse";
import CheckStudentInfo from "@/views/CheckStudentInfo";
import CheckTeacherInfo from "@/views/CheckTeacherInfo";
import CheckSchool from "@/views/CheckSchool";
import CheckMajor from "@/views/CheckMajor";
import CheckBuilding from "@/views/CheckBuilding";
import CheckClassroom from "@/views/CheckClassroom";
import CheckTime from "@/views/CheckTime";
import CheckApplication from "@/views/CheckApplication";
import TeacherInfo from "@/views/TeacherInfo";
import StudentInfo from "@/views/StudentInfo";
import TeacherCourse from "@/views/TeacherCourse";
import StudentChooseCourse from "@/views/StudentChooseCourse";
import StudentCheckCourse from "@/views/StudentCheckCourse";
import CheckCourseOpen from "@/views/CheckCourseOpen";
import TeacherApplication from "@/views/TeacherApplication";
import StudentApply from "@/views/StudentApply";
import StudentViewApplication from "@/views/StudentViewApplication";
import StudentViewTable from "@/views/StudentViewTable";

export const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: "/:catchAll(.*)",
    redirect: '/login'
  }
  ]

export const admin_routes =
  {
    path: '/admin',
    name: 'Admin',
    component: Admin,
    children: [
        {
            path: 'checkstudentinfo',
            name: 'CheckStudentInfo',
            component: CheckStudentInfo
        },
        {
            path: 'checkteacherinfo',
            name: 'CheckTeacherInfo',
            component: CheckTeacherInfo
        },
        {
            path: 'checkschool',
            name: 'CheckSchool',
            component: CheckSchool
        },
        {
            path: 'checkmajor',
            name: 'CheckMajor',
            component: CheckMajor
        },
        {
            path: 'checkbuilding',
            name: 'CheckBuilding',
            component: CheckBuilding
        },
        {
            path: 'checkclassroom',
            name: 'CheckClassroom',
            component: CheckClassroom
        },
        {
            path: 'checktime',
            name: 'CheckTime',
            component: CheckTime
        },
        {
            path: 'checkcourse',
            name: 'CheckCourse',
            component: CheckCourse
        },
        {
            path: 'addcourse',
            name: 'AddCourse',
            component: AddCourse
        },
        {
            path: 'editcourse/:id',
            name: 'EditCourse',
            component: EditCourse,
            props(params) {
                return {
                    data: params.data,
                    id: params.id
                }
            }
        },
        {
            path: 'checkapplication',
            name: 'CheckApplication',
            component: CheckApplication
        },
        {
            path: 'checkcourseopen',
            name: 'CheckCourseOpen',
            component: CheckCourseOpen
        },
    ]
  }

export const teacher_routes =
  {
    path: '/teacher',
    name: 'Teacher',
    component: Teacher,
    children: [
      {
        path: 'teacherinfo',
        name: 'TeacherInfo',
        component: TeacherInfo
      },
      {
        path: 'teachercourse',
        name: 'TeacherCourse',
        component: TeacherCourse
      },
      {
        path: 'teacherapplication',
        name: 'TeacherApplication',
        component: TeacherApplication
      }
    ]
  }

export const student_routes =
  {
    path: '/student',
    name: 'Student',
    component: Student,
    children: [
      {
        path: 'studentinfo',
        name: 'StudentInfo',
        component: StudentInfo
      },
      {
        path: 'studentchoosecourse',
        name: 'StudentChooseCourse',
        component: StudentChooseCourse
      },
      {
        path: 'studentcheckcourse',
        name: 'StudentCheckCourse',
        component: StudentCheckCourse
      },
    {
        path: 'studentapply',
        name: 'StudentApply',
        component: StudentApply
    },
    {
        path: 'studentviewapplication',
        name: 'StudentViewApplication',
        component: StudentViewApplication
    },
    {
        path: 'studentviewtable',
        name: 'StudentViewTable',
        component: StudentViewTable
    }
    ]
  }

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

// router.beforeEach((to, from) => {
//     
//
//     //sessionStorage.setItem("user", JSON.stringify(res.data.data))
//     //
//     if (!sessionStorage.getItem('routes')) {
//         
//         sessionStorage.setItem('routes', JSON.stringify(router.getRoutes()));
//         //store.commit('change_routes', router.getRoutes())
//     }
//
//     let store_routes = JSON.parse(sessionStorage.getItem('routes'))
//     let now_routes = router.getRoutes()
//     if (now_routes.length < store_routes.length) {
//         router.addRoute(admin_routes)
//         store_routes.forEach(item => {
//             
//             
//             
//             
//
//             if (!now_routes.includes(item)) {
//                 router.addRoute({
//                         path: item.path,
//                         component: item.components.default.__file,
//                         name: item.name
//                     })
//                 // router.addRoute(item)
//             }
//         })
//     }
//     // if (router.getRoutes().find(item => item.name === to.name)) {
//     //     this.$store.routes_store.forEach(item => {
//     //         if (item.name === to.name) {}
//     //     })
//     //
//     // }
//     
//     
//     
//     // if (to.matched.length === 0) { router.push(to.path)}
//     //router.push(to.path)
//     return true
// })

// router.afterEach((to, from) => {
//     if (from.path === "/login") {
//         
//         
//         if (store.state.routes_store.length < router.getRoutes().length) {
//             store.commit('change_routes', router.getRoutes())
//         }
//         
//     }
// })

export default router

