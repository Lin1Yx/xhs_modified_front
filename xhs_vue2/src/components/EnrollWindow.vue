<template>
    
    <div>
      <el-backtop target=".page-component__scroll .el-scrollbar__wrap"></el-backtop>
        <div class="personal">
            <el-container>
                <el-header>
                    
                </el-header>
                <el-main>
                    
                      <el-dialog
                          title="资料修改"
                          :visible.sync="dialogVisible"
                          width="40%"
                          :before-close="handleClose">
                          <el-form ref="ruleForm" 
                                :model="form" 
                                label-position="left"
                                label-width="80px" 
                                :rules="rules">
                            <el-form-item label="头像">
                              <el-row>
                              <!--读取数据库存储的头像 路径写在src里-->
                                <el-avatar :src="'data:image/png;base64,'+imageBase64" :size="80" align="right"></el-avatar>
                                <el-upload class="avatar-uploader inline-block" 
                                          action="http://localhost:8080/users/updateHeadPortrait"
                                          :show-file-list="false" 
                                          :on-success="handleAvatarSuccess" 
                                          :before-upload="beforeAvatarUpload"
                                          :data={userId:this.id}>
                                          <!--action写上传的url路径-->          
                                <el-button type="primary" size="mini">修改头像</el-button>
                              </el-upload>
                            </el-row>
                          </el-form-item>
                          <el-form-item label="昵称">
                            <el-input
                              placeholder="请输入昵称" 
                              v-model="form.name"
                              clearable
                              maxlength="10"
                              show-word-limit
                              >
                              <!--读取数据库里的用户名作为placeholder-->
                            </el-input>
                          </el-form-item>
                          <el-form-item label="性别">
                            <el-radio-group v-model="form.sex">
                              <el-radio label="男"></el-radio>
                              <el-radio label="女"></el-radio>
                            </el-radio-group>
                          </el-form-item>
          
                          <el-form-item label="手机号码" 
                              prop="phone">
                              <el-col>
                            <el-input v-model="form.phone"
                                placeholder="请输入手机号码"
                                clearable></el-input></el-col>
                          </el-form-item>

                          <el-form-item label="生日">
                            <el-col :span="5">
                              <el-date-picker
                                v-model="form.bith"
                                type="date"
                                placeholder="选择日期"
                                :picker-options="pickerOptions">
                              </el-date-picker>
                            </el-col>
                          </el-form-item>
                          <!-- <el-form-item label="新密码" 
                              prop="pass">
                            <el-input type="password" v-model="form.pass" autocomplete="off"></el-input>
                          </el-form-item>
                          <el-form-item label="确认密码" 
                              prop="checkPass">
                            <el-input type="password" v-model="form.checkPass" autocomplete="off"></el-input>
                          </el-form-item> -->
                          <el-form-item>
                            <el-button @click="dialogVisible = false">取 消</el-button>
                            <el-button type="primary" @click="dialogVisible = false,submitForm('ruleForm')">保 存</el-button>
                          </el-form-item>
                          </el-form>
                      </el-dialog>

                      <!-- <el-container>
                        <el-aside width="200px"> -->
                          <div class="block"><el-avatar :size="180" :fit="fit" :src="require('../assets/head1.webp')"></el-avatar></div>
                        <!-- </el-aside> -->
                        <!-- <el-main> -->
                          <h3 class="user_name">Avenir.</h3><!--读取数据库的用户名-->
                        <span class="id">小红书号:</span>
                        <span class="id" src="form.id"></span><!--读取数据库的账号-->
                          <el-row>
                        <span class="sign">签名</span>
                          </el-row>
                        <el-row>
                          <el-button type="primary" icon="el-icon-edit" round class="edit" size="small" @click="dialogVisible = true">资料修改</el-button>
                        </el-row>
                        <el-row :gutter="20">
                        <el-col :span="8" :offset="10"><div>
                          <el-table
                              :data="tableData"
                              style="width:210px"
                              >
                              <el-table-column
                                prop="guanzhu"
                                label="关注"
                                width="60"
                                align="center">
                              </el-table-column>
                              <el-table-column
                                prop="fans"
                                label="粉丝"
                                width="60"
                                align="center">
                              </el-table-column>
                              <el-table-column
                                prop="liked"
                                label="获赞与收藏"
                                width="90"
                                align="center">
                              </el-table-column>
                              </el-table>
                          </div></el-col>
                        </el-row>
                        <!-- </el-main>
                      </el-container> -->
                </el-main>
                <el-footer>
                  <el-row type="flex" justify="center">
                      <el-col :span="15" :offset="11">
                  <el-tabs v-model="activeName" @tab-click="handleClick">
                    <el-tab-pane label="笔记" name="first">
                      
                    </el-tab-pane>
                    <el-tab-pane label="收藏" name="second">
                      
                    </el-tab-pane>
                    <el-tab-pane label="点赞" name="third">
                      
                    </el-tab-pane>
                  </el-tabs>
                </el-col>
                    </el-row> 
                </el-footer>
            </el-container>
        </div>
    </div>
</template>

<script>
// 重置密码和取消登录的函数
// import { ResetPassword } from '@/api/user'
// import { removeLoginID } from '@/utils/auth'
  export default {
    data () {
      var phoneReg = /^[1][3,4,5,7,8][0-9]{9}$/
      var validatePhone = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('号码不能为空!!'))
        }
        setTimeout(() => {
          if (!phoneReg.test(value)) {
            callback(new Error('格式有误'))
          } else {
            callback()
          }
        }, 100)
      };
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else {
          if (this.ruleForm.checkPass !== '') {
            this.$refs.ruleForm.validateField('checkPass');
          }
          callback();
        }
      };
      var validatePass2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.ruleForm.pass) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };

      return {
        pickerOptions: {
          disabledDate(time) {
            return time.getTime() > Date.now();
          },
        },
        ruleForm: {
          phone: [
            {
              required: true, // required就是这个框必须填写
              validator: validatePhone, // 规则
              trigger: 'blur', // blur失去焦点，事件何时触发
            },
          ],
          rules: {
            pass: [
              { validator: validatePass, trigger: 'blur' }
            ],
            checkPass: [
              { validator: validatePass2, trigger: 'blur' }
            ]
          },
      },
        tableData: [{
            guanzhu:'0',
            fans:'0',
            liked:'0'
          }],
        activeName:"first",
        dialogVisible: false,
        imageUrl: '',
        form: {
          name: '',
          sex:'',
          birth:'2003-10-03',
          phone: '12345678',
          pass: '',
          checkPass: '',
          age: '',
          id:'111',
          guanzhu:'0',
          fans:'0',
          liked:'0'
        },
      }
    },
    methods: {
      handleClick(tab, event) {
        console.log(tab, event);
      },
      handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
      },
      handleAvatarSuccess(res, file) {
        this.imageUrl = URL.createObjectURL(file.raw);
      },
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!');
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!');
        }
        return isJPG && isLt2M;
      },
      // goBack() {//回到首页
      //   this.$router.go(-1)
      // },
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            const data = {
              newPassword: this.ruleForm.pass
            }
            //重置密码的函数调用
            // ResetPassword(data).then(res => {
            //   //取消登录状态
            //   removeLoginID()
            //   this.$confirm('密码已修改，将重新登录', '提示', {
            //     confirmButtonText: '确定',
            //     showCancelButton: false,
            //     closeOnClickModal: false,
            //     closeOnPressEscape: false,
            //     showClose: false,
            //     type: 'warning'
            //   }).then(action => {
            //     if (action === 'confirm') {
            //       // this.$router.replace('/login')回到主页
            //     }
            //   })
            // })
          } else {
            return false
          }
        })
      },
      },
    }
</script>

<style>
  .el-header, .el-footer {
    background-color: #FFFFFF;
    color: #303133;
    text-align: center;
    line-height: 60px;
  }
  
  .el-main {
    background-color: #FFFFFF;
    color: #303133;
    text-align: center;
    line-height: 30px;
  }
  
  body > .el-container {
    margin-bottom: 40px;
  }
  .user_name{
    letter-spacing:1px;
    margin-top:0px;
    margin-bottom:1px;
  }
  .id{
    color:#909399;
    letter-spacing:1px;
    margin-top:0px;
    margin-bottom:1px;
    font-weight:500;
    font-size:10px;
  }
  .sign{
    letter-spacing:1px;
    margin-top:0px;
    margin-bottom:1px;
    font-weight:500;
    font-size:13px;
  }
  .input{
    el-form:inline=true;
  }
  .el-row {
    margin-bottom: 20px;
    &:last-child {
      margin-bottom: 0;
    }
  }
  .el-col {
    border-radius: 0px;
  }
  .row-bg {
    padding: 10px 0;
  }
  .el-table{
    border:none;
    fit:"true";
    size:"mini";
    text-align:center;
    color:#303133;
    font-weight:400;
    
  }
  .group > .el-table--enable-row-hover .el-table__body tr:hover > td {
  background-color: transparent !important;
}
.inline-block{
  display:inline-block;
  vertical-align: middle;
}
</style>

<style lang="scss" scoped>
//去掉el-tab-pane底部灰色线条
.el-tabs__nav-wrap::after {
  height: 0 !important;
}
.el-tabs /deep/.el-tabs__nav-wrap::after{
   background-color: #fff;
}
//修改样式
/deep/.el-tabs__item {
  padding: 0 20px 0 0;
}
.edit{
  color: white;
  // background-color:red;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar {
  width: 160px;
  height: 160px;
}
</style>
