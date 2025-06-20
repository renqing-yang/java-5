package week3;

/**
 * primary key / candidate key / super key
 * normalization vs denormalization
 *      1st : dun put multiple values in same cell
 *          id, name
 *              fn,ln
 *      2nd : non-prime attributes fully depend on prime attribute
 *          val a -> val b    a determine b,  b depends on a
 *          val a -> val b
 *
 *          (book_id, author_id) book_name, author_name
 *             1        a       java        Tom
 *             1        b       java        Jerry
 *             2        a       C#          Tom
 *
 *           book_name depends on book_id
 *           book_name not depends on author_id
 *
 *           book_name not fully depends on prime attribute
 *      3rd : no transitive relation between non-prime attribute
 *          (id), name, address_id, street
 *          street depends on address_id depends on id
 *
 * 1-1, 1-m, m-m : find relationships between entities / objects
 * one class can have many students, one student belongs to one class :  1 - m
 *      class (id, name)
 *      student (id, name, class_id(fk))
 *
 * one class has 1 student, and 1 student only belongs to 1 class : 1 - 1
 *      class (id, name)
 *      student (id, name, class_id(fk))
 *
 *      class (id, name, stu_id)
 *      student (id, name)
 *
 * one class can have many students, one students can take many classes : m - m
 *      class (id, name)
 *      student_class (id, class_id(fk), stu_id(fk))
 *      student (id, name)
 *
 *
 * entity value attribute
 *
 *  document
 *  id, description
 *  1 ,  for ..
 *  2 , this is ...
 *
 *  document_details
 *  id, document_id, col_name, col_type, col_val
 *  a1 ,     1     ,  "name" , "string", "Tom"
 *  a2 ,     1     ,  "gender", "string", "male"
 *  a3,      1     ,  "age"   , "int"   , ..
 *  a4,      2     ,  "car_name", "string" , "bmw"
 *
 */