#include "cn_hw4.h"
#include "ui_cn_hw4.h"

CN_HW4::CN_HW4(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::CN_HW4)
{
    ui->setupUi(this);
}

CN_HW4::~CN_HW4()
{
    delete ui;
}

void CN_HW4::on_pushButton_matrix1_clicked()
{
    matrixFile1 = QFileDialog::getOpenFileName(this, "Select matrix file 1", ".", "Text files (*.txt);; All files (*.*)");
    ui->textBrowser->append("File matrix 1 set !");
    ui->textBrowser->append(matrixFile1);

    ui->textBrowser->append("Load matrixFile1 data in matrix a");
    ifstream cin(matrixFile1.toLatin1().data());
    a = new SparseMatrix(cin, 0);

    if(ui->checkBoxLogs->isChecked()){
        QString outputInfo;
        for(int i = 1; i <= a->getN(); i++){
            for(lista p = a->M[i]; p != NULL; p=p->next){
                outputInfo.append(QString::number(p->val));
                outputInfo.append(", ");
                outputInfo.append(QString::number(i-1));
                outputInfo.append(", ");
                outputInfo.append(QString::number(p->col-1));
            }
            ui->textBrowser->append(outputInfo);
            outputInfo.clear();
        }
    }
}

void CN_HW4::on_pushButton_matrix2_clicked()
{
    matrixFile2 = QFileDialog::getOpenFileName(this, "Select matrix file 2", ".", "Text files (*.txt);; All files (*.*)");
    ui->textBrowser->append("File matrix 2 set !");
    ui->textBrowser->append(matrixFile2);
    ui->textBrowser->append("Load matrixFile2 data in matrix b");

    ifstream cin(matrixFile2.toLatin1().data());
    b = new SparseMatrix(cin, 0);

    if(ui->checkBoxLogs->isChecked()){
        QString outputInfo;
        for(int i = 1; i <= b->getN(); i++){
            for(lista p = b->M[i]; p != NULL; p=p->next){
                outputInfo.append(QString::number(p->val));
                outputInfo.append(", ");
                outputInfo.append(QString::number(i-1));
                outputInfo.append(", ");
                outputInfo.append(QString::number(p->col-1));
            }
            ui->textBrowser->append(outputInfo);
            outputInfo.clear();
        }
    }
}

void CN_HW4::on_sumMatrixFile_button_clicked()
{
    sumMatrixFile = QFileDialog::getOpenFileName(this, "Select sum matrix file", ".", "Text files (*.txt);; All files (*.*)");
    ui->textBrowser->append("File sum matrix 2 set");
    ui->textBrowser->append(sumMatrixFile);

    ui->textBrowser->append("Load sumMatrixFile data in sumRef");
    ifstream cin(sumMatrixFile.toLatin1().data());
    sumRef = new SparseMatrix(cin, 0);

    if(ui->checkBoxLogs->isChecked()){
        QString outputInfo;
        for(int i = 1; i <= sumRef->getN(); i++){
            for(lista p = sumRef->M[i]; p != NULL; p=p->next){
                outputInfo.append(QString::number(p->val));
                outputInfo.append(", ");
                outputInfo.append(QString::number(i-1));
                outputInfo.append(", ");
                outputInfo.append(QString::number(p->col-1));
            }
            ui->textBrowser->append(outputInfo);
            outputInfo.clear();
        }
    }
}

void CN_HW4::on_prodMatrixFile_button_clicked()
{
    prodMatrixFile = QFileDialog::getOpenFileName(this, "Select prod matrix file", ".", "Text files (*.txt);; All files (*.*)");
    ui->textBrowser->append("File prod matrix set !");
    ui->textBrowser->append(prodMatrixFile);

    ui->textBrowser->append("Load prodMatrixFile data in prodRef obj");
    ifstream cin(prodMatrixFile.toLatin1().data());
    prodRef = new SparseMatrix(cin, 0);

    if(ui->checkBoxLogs->isChecked()){
        QString outputInfo;
        for(int i = 1; i <= prodRef->getN(); i++){
            for(lista p = prodRef->M[i]; p != NULL; p=p->next){
                outputInfo.append(QString::number(p->val));
                outputInfo.append(", ");
                outputInfo.append(QString::number(i-1));
                outputInfo.append(", ");
                outputInfo.append(QString::number(p->col-1));
            }
            ui->textBrowser->append(outputInfo);
            outputInfo.clear();
        }
    }
}

void CN_HW4::sum(){
    if(matrixFile1 == "" || matrixFile2 == "" || sumMatrixFile == ""){
        ui->textBrowser->append("File matrix  1 or 2 or sumMatrixFile not set");
        return;
    }

    time(&start);
    SparseMatrix sumRes = *a+*b;
    time(&end);

    if(sumRes == *sumRef){

        ui->textBrowser->append("Sum correct ! ");
        cout<<"He He Sum correct!!!!\n";
    }
    else{
        cout<<"Pfffff wrong sum!!!!!\n";
        ui->textBrowser->append("Sum incorrect ! ");
    }

    ui->textBrowser->append("Time :  ");
    ui->textBrowser->append(QString::number(difftime(end, start)));

    if(ui->checkBoxLogs->isChecked()){
        QString outputInfo;
        for(int i = 1; i <= sumRes.getN(); i++){
            for(lista p = sumRes.M[i]; p != NULL; p=p->next){
                outputInfo.append(QString::number(p->val));
                outputInfo.append(", ");
                outputInfo.append(QString::number(i-1));
                outputInfo.append(", ");
                outputInfo.append(QString::number(p->col-1));
            }
            ui->textBrowser->append(outputInfo);
            outputInfo.clear();
        }
    }
}
void CN_HW4::prod(){
    if(matrixFile1 == "" || matrixFile2 == "" || prodMatrixFile == ""){
        ui->textBrowser->append("File matrix  1 or 2 or prodMatrixFile not set");
        return;
    }

    time(&start);
    SparseMatrix prodRes = (*a) * (*b);
    time(&end);

    if(prodRes == *prodRef){
        ui->textBrowser->append("Prod correct ! ");
        cout<<"He He Prod correct!!!!\n";
    }else{
        ui->textBrowser->append("Prod incorrect ! ");
        cout<<"Pfffff wrong prod!!!!!\n";
    }
    ui->textBrowser->append("Time :  ");
    ui->textBrowser->append(QString::number(difftime(end, start)));

    if(ui->checkBoxLogs->isChecked()){
        QString outputInfo;
        for(int i = 1; i <= prodRes.getN(); i++){
            for(lista p = prodRes.M[i]; p != NULL; p=p->next){
                outputInfo.append(QString::number(p->val));
                outputInfo.append(", ");
                outputInfo.append(QString::number(i-1));
                outputInfo.append(", ");
                outputInfo.append(QString::number(p->col-1));
            }
            ui->textBrowser->append(outputInfo);
            outputInfo.clear();
        }
    }
}

void CN_HW4::on_pushButton_prod_clicked()
{
    prod();
}

void CN_HW4::on_pushButton_sum_clicked()
{
    sum();
}

void CN_HW4::on_pushButton_extraTest_clicked()
{
    SparseMatrix X;
    X.allocEmpty(a->getN());
    for (int i=1; i<=X.getN(); ++i) {
        lista v=new celula;
        v->val=i;
        v->col=1;
        X.insertEl(i,v);
    }

    SparseMatrix e = (*a) * X;

    if(*vecA == e){
        ui->textBrowser->append("A*X==VA correct! ");
        cout<<"A*X==VA correct \n";
    }else{
        ui->textBrowser->append("A*X != VA incorrect! ");
        cout<<"A*X==VA incorrect \n";
    }
}

void CN_HW4::on_pushButton_fileButtonVecA_clicked()
{
    vecAFile = QFileDialog::getOpenFileName(this, "Select vecA file", ".", "Text files (*.txt);; All files (*.*)");
    ui->textBrowser->append("File vecA set");
    ui->textBrowser->append(vecAFile);

    ui->textBrowser->append("Load vecA data in vecAFile");
    ifstream cin(vecAFile.toLatin1().data());
    vecA = new SparseMatrix(cin, 1);

    if(ui->checkBoxLogs->isChecked()){
        QString outputInfo;
        for(int i = 1; i <= vecA->getN(); i++){
            for(lista p = vecA->M[i]; p != NULL; p=p->next){
                outputInfo.append(QString::number(p->val));
                outputInfo.append(", ");
                outputInfo.append(QString::number(i-1));
                outputInfo.append(", ");
                outputInfo.append(QString::number(p->col-1));
            }
            ui->textBrowser->append(outputInfo);
            outputInfo.clear();
        }
    }
}
